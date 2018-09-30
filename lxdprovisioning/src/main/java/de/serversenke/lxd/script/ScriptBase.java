package de.serversenke.lxd.script;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import de.serversenke.lxd.client.BaseMgmt;
import de.serversenke.lxd.client.core.ClientKeyStore;
import de.serversenke.lxd.client.listener.DefaultOperationListener;
import de.serversenke.lxd.client.listener.ProgressListener;
import de.serversenke.lxd.script.exception.CertificateException;
import de.serversenke.lxd.script.exception.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;

@Slf4j
public class ScriptBase extends BaseMgmt {

    protected LinkedHashMap<?, ?> yaml;

    public ScriptBase(LinkedHashMap<?, ?> yaml) {
        this.yaml = yaml;

        this.lxdServer = (String)Utils.get(yaml, "lxd", "host") + ":" + (Integer)Utils.get(yaml, "lxd", "port");
        this.defaultContainerName = Utils.get(yaml, "container", "configuration", "name");
    }

    final protected static ProgressListener listener = new ProgressListener() {
            @Override
            public void updateProgress(String progress) {
                log.info("Progress: " + progress);
            }
        };

    final protected static DefaultOperationListener opListener = new DefaultOperationListener() {
            @Override
            public void onMessage(String text) {
                // log.info(text.replaceAll("\\s+$", ""));
                System.out.print(text);
            }

            @Override
            public void onMessage(ByteString bytes) {
                // log.info(bytes.utf8().replaceAll("\\s+$", ""));
                System.out.print(bytes.utf8());
            }
        };

    protected void registerCertKey() {
        String certFile = null;

        try {
            certFile = Utils.get(yaml, "lxd", "certFile");
        } catch (Exception e) {
            throw new ConfigurationException("Configuration key 'certFile' in 'lxd' not found");
        }

        String clientKeyFile = null;
        try {
            clientKeyFile = Utils.get(yaml,  "lxd", "keyFile");
        } catch (Exception e) {
            throw new ConfigurationException("Configuration 'keyFile' in 'lxd' not found");
        }

        // register certificate/key
        Path certificatePath = FileSystems.getDefault().getPath(certFile, new String[0]);
        Path keyPath = FileSystems.getDefault().getPath(clientKeyFile, new String[0]);

        String certificate = null;
        String clientKey = null;
        try {
            certificate = new String(Files.readAllBytes(certificatePath), StandardCharsets.UTF_8);
            clientKey = new String(Files.readAllBytes(keyPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CertificateException("Unable to read certificate or client key file", e);
        }

        try {
            ClientKeyStore.addClientKey(clientKey, certificate, "");
        } catch (Exception e) {
            throw new CertificateException("Unable to register certificate and client key", e);
        }
    }

}
