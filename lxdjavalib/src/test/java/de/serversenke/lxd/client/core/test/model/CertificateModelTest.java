package de.serversenke.lxd.client.core.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import de.serversenke.lxd.client.core.model.Certificate;
import de.serversenke.lxd.client.core.model.CertificatePost;
import de.serversenke.lxd.client.core.model.CertificatePut;

public class CertificateModelTest extends TestBase {

    @Test
    void testCertificateFingerprintPatchDeSerialize() {
        testDeSerialize("request_certificate_fingerprint_patch.json", CertificatePut.class);
    }

    @Test
    void testCertificateFingerprintPutDeSerialize() {
        testDeSerialize("request_certificate_fingerprint_put.json", CertificatePut.class);
    }

    @Test
    void testCertificateFingerprintPostDeSerialize() {
        testDeSerialize("response_certificate_fingerprint.json", Certificate.class);
    }

    @Test
    void testCertificatePostDeSerialize() {
        testDeSerialize("request_certificate_post.json", CertificatePost.class);
    }

    @Test
    void testCertificateDeSerialize() {
        String json = getJson("response_certificate.json");
        Gson gson = getGson();

        String[] target1;
        String[] target2;
        String output1;
        String output2;

        // 1. (de)serialization
        target1 = gson.fromJson(json, String[].class);
        output1 = gson.toJson(target1);

        // 2. (de)serialization
        target2 = gson.fromJson(output1, String[].class);
        output2 = gson.toJson(target2);

        assertEquals(output1, output2, "server (de)serialization failed");
        assertEquals(target1[0], target2[0], "server (de)serialization failed");
    }
}
