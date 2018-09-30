package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.Certificate;
import de.serversenke.lxd.client.core.model.CertificatePost;
import de.serversenke.lxd.client.core.model.CertificatePut;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LxdCertificates {

    @GET("/1.0/certificates")
    public Call<LxdResponse<String[]>> list();

    @POST("/1.0/certificates")
    public Call<LxdResponse<Empty>> add(@Body CertificatePost cert);

    @GET("/1.0/certificates/{fingerprint}")
    public Call<LxdResponse<Certificate>> get(@Path("fingerprint") String fingerprint);

    @PUT("/1.0/certificates/{fingerprint}")
    public Call<LxdResponse<Empty>> replaceProperties(@Path("fingerprint") String fingerprint, @Body CertificatePut properties);

    @PATCH("/1.0/certificates/{fingerprint}")
    public Call<LxdResponse<Empty>> updateProperties(@Path("fingerprint") String fingerprint, @Body CertificatePut properties);

    @DELETE("/1.0/certificates/{fingerprint}")
    public Call<LxdResponse<Empty>> delete(@Path("fingerprint") String fingerprint);
}
