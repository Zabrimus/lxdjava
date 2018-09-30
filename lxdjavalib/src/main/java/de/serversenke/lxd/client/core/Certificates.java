package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.Certificate;
import de.serversenke.lxd.client.core.model.CertificatePost;
import de.serversenke.lxd.client.core.model.CertificatePut;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.rest.LxdCertificates;
import lombok.SneakyThrows;

public class Certificates extends ApiBase {

    protected LxdCertificates lxdCertificates;

    @SneakyThrows
    public Certificates(String url) {
        lxdCertificates = getRetrofit(url).create(LxdCertificates.class);
    }

    /**
     * GET /1.0/certificates
     */
    public ClientResponse<String[]> list() {
        return execute(lxdCertificates.list());
    }

    /**
     * POST /1.0/certificates
     */
    public ClientResponse<Empty> add(CertificatePost cert) {
        return execute(lxdCertificates.add(cert));
    }

    /**
     * GET /1.0/certificates/{fingerprint}
     */
    public ClientResponse<Certificate> get(String fingerprint) {
        return execute(lxdCertificates.get(fingerprint));
    }

    /**
     * PUT /1.0/certificates/{fingerprint}
     */
    public ClientResponse<Empty> replaceProperties(String fingerprint, CertificatePut properties) {
        return execute(lxdCertificates.replaceProperties(fingerprint, properties));
    }

    /**
     * PATCH /1.0/certificates/{fingerprint}
     */
    public ClientResponse<Empty> updateProperties(String fingerprint, CertificatePut properties) {
        return execute(lxdCertificates.updateProperties(fingerprint, properties));
    }

    /**
     * DELETE /1.0/certificates/{fingerprint}
     */
    public ClientResponse<Empty> delete(String fingerprint) {
        return execute(lxdCertificates.delete(fingerprint));
    }
}
