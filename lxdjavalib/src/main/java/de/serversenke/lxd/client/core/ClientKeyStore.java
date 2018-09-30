package de.serversenke.lxd.client.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import okhttp3.OkHttpClient;

// PEM import found at https://stackoverflow.com/questions/2138940/import-pem-into-java-key-store
public class ClientKeyStore {

    private final static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }
    } };

    private final static HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static KeyStore keystore;
    private static SSLContext sslContext;

    private ClientKeyStore() {
        // disable construction
    }

    public static synchronized void addClientKey(String privateKeyPem, String certificatePem, final String password) {
        if (keystore == null) {
            try {
                keystore = KeyStore.getInstance("JKS");
                keystore.load(null);
            } catch (Exception e) {
                throw new RuntimeException("Unable to create keyStore", e);
            }
        }

        // add private key / certificate
        try {
            final PrivateKey key = createPrivateKey(privateKeyPem);
            final X509Certificate[] cert = createCertificates(certificatePem);
            keystore.setKeyEntry(cert[0].getSubjectX500Principal().getName(), key, (password != null ? password : "").toCharArray(), cert);
        } catch (Exception e) {
            throw new RuntimeException("Unable to import client key/certificate", e);
        }

        // recreate SSLContext
        try {
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keystore, "".toCharArray());
            final KeyManager[] km = kmf.getKeyManagers();

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(km, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            throw new RuntimeException("Unable to create SSLContext", e);
        }
    }

    public synchronized static OkHttpClient.Builder addSecurity(OkHttpClient.Builder builder) {
        if (sslContext == null) {
            System.err.println("SSLContext is not yet initialized! Is lxd.rest.client.ClientKeyStore.addClientKey() really called?");
            return builder;
        }
        try {
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(hostnameVerifier);

            return builder;
        } catch (Exception e) {
            throw new RuntimeException("Unable to setup OkHttpClient security", e);
        }
    }


    private static PrivateKey createPrivateKey(String privateKeyPem) throws Exception {
        final BufferedReader r = new BufferedReader(new StringReader(privateKeyPem));
        String s = r.readLine();
        if (s == null || !s.contains("BEGIN PRIVATE KEY")) {
            r.close();
            throw new IllegalArgumentException("No PRIVATE KEY found");
        }
        final StringBuilder b = new StringBuilder();
        s = "";
        while (s != null) {
            if (s.contains("END PRIVATE KEY")) {
                break;
            }
            b.append(s);
            s = r.readLine();
        }
        r.close();
        final String hexString = b.toString();
        final byte[] bytes = DatatypeConverter.parseBase64Binary(hexString);
        return generatePrivateKeyFromDER(bytes);
    }

    private static X509Certificate[] createCertificates(String certificatePem) throws Exception {
        final List<X509Certificate> result = new ArrayList<X509Certificate>();
        final BufferedReader r = new BufferedReader(new StringReader(certificatePem));

        String s = r.readLine();
        if (s == null || !s.contains("BEGIN CERTIFICATE")) {
            r.close();
            throw new IllegalArgumentException("No CERTIFICATE found");
        }
        StringBuilder b = new StringBuilder();
        while (s != null) {
            if (s.contains("END CERTIFICATE")) {
                String hexString = b.toString();
                final byte[] bytes = DatatypeConverter.parseBase64Binary(hexString);
                X509Certificate cert = generateCertificateFromDER(bytes);
                result.add(cert);
                b = new StringBuilder();
            } else {
                if (!s.startsWith("----")) {
                    b.append(s);
                }
            }
            s = r.readLine();
        }
        r.close();

        return result.toArray(new X509Certificate[result.size()]);
    }

    private static RSAPrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    private static X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {
        final CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }

}
