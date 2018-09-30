package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.ProfilePost;
import de.serversenke.lxd.client.core.model.ProfilePostRename;
import de.serversenke.lxd.client.core.model.ProfilePut;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LxdProfiles {

    @GET("/1.0/profiles")
    public Call<LxdResponse<String[]>> list();

    @POST("/1.0/profiles")
    public Call<LxdResponse<Empty>> create(@Body ProfilePost newProfile);

    @GET("/1.0/profiles/{name}")
    public Call<LxdResponse<Empty>> get(@Path("name") String name);

    @PUT("/1.0/profiles/{name}")
    public Call<LxdResponse<Empty>> replace(@Path("name") String name, @Body ProfilePut replacement);

    @PATCH("/1.0/profiles/{name}")
    public Call<LxdResponse<Empty>> update(@Path("name") String name, @Body ProfilePut replacement);

    @POST("/1.0/profiles/{name}")
    public Call<LxdResponse<Empty>> rename(@Path("name") String name, @Body ProfilePostRename newName);

    @DELETE("/1.0/profiles/{name}")
    public Call<LxdResponse<Empty>> delete(@Path("name") String name);

}
