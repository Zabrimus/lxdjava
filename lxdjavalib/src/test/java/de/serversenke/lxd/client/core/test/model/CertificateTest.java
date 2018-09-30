package de.serversenke.lxd.client.core.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.Certificates;
import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.model.Certificate;
import de.serversenke.lxd.client.core.model.CertificatePost;
import de.serversenke.lxd.client.core.model.CertificatePut;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.StatusCode;
import retrofit2.Call;

public class CertificateTest extends TestBase {

    class MyServer<TT> extends Certificates {

        private Call<LxdResponse<TT>> request;

        public MyServer(Call<LxdResponse<TT>> req, String url) throws Exception {
            super(url);
            request = req;
        }

        @SuppressWarnings("unchecked")
        protected <T> ClientResponse<T> execute(Call<LxdResponse<T>> dummy) {
            return (ClientResponse<T>) super.execute((Call<LxdResponse<TT>>) request);
        }
    }

    @Test
    void testCertificateList() {
        String[] responseObject = createObject("response_certificate.json", String[].class);
        Call<LxdResponse<String[]>> call = createCall(responseObject);

        ClientResponse<String[]> response = null;
        try {
            response = new MyServer<String[]>(call, "http://127.0.0.1").list();
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testCertificateAdd() {
        CertificatePost request = createObject("request_certificate_post.json", CertificatePost.class);
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").add(request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

    @Test
    void testCertificateGet() {
        Certificate responseObject = createObject("response_certificate_fingerprint.json", Certificate.class);
        Call<LxdResponse<Certificate>> call = createCall(responseObject);

        ClientResponse<Certificate> response = null;
        try {
            response = new MyServer<Certificate>(call, "http://127.0.0.1").get("fingerprint");
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testCertificateReplaceProperies() {
        CertificatePut request = createObject("request_certificate_fingerprint_put.json", CertificatePut.class);
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").replaceProperties("fingerprint", request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

    @Test
    void testCertificateUpdateProperies() {
        CertificatePut request = createObject("request_certificate_fingerprint_patch.json", CertificatePut.class);
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").updateProperties("fingerprint", request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

    @Test
    void testCertificateDelete() {
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").delete("fingerprint");
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }
}
