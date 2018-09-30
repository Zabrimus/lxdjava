package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.Network;
import de.serversenke.lxd.client.core.model.NetworkPost;
import de.serversenke.lxd.client.core.model.NetworkPostRename;
import de.serversenke.lxd.client.core.model.NetworkPut;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LxdNetworks {

    @GET("/1.0/networks")
    public Call<LxdResponse<String[]>> list();

    @POST("/1.0/networks")
    public Call<LxdResponse<Empty>> create(@Body NetworkPost newNetwork);

    @GET("/1.0/networks/{name}")
    public Call<LxdResponse<Network>> get(@Path("name") String name);

    @PUT("/1.0/networks/{name}")
    public Call<LxdResponse<Empty>> replace(@Path("name") String name, @Body NetworkPut replacement);

    @PATCH("/1.0/networks/{name}")
    public Call<LxdResponse<Empty>> update(@Path("name") String name, @Body NetworkPut replacement);

    @PATCH("/1.0/networks/{name}")
    public Call<LxdResponse<Empty>> rename(@Path("name") String name, @Body NetworkPostRename newName);

    @DELETE("/1.0/networks/{name}")
    public Call<LxdResponse<Empty>> delete(@Path("name") String name);
}
