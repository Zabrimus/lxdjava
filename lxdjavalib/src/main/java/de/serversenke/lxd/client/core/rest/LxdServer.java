package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.ServerConfiguration;
import de.serversenke.lxd.client.core.model.ServerConfigurationPut;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;

public interface LxdServer {

      @GET("/")
      public Call<LxdResponse<String[]>> listSupportedApi();

      @GET("/1.0")
      public Call<LxdResponse<ServerConfiguration>> getServer();

      @PUT("/1.0")
      public Call<LxdResponse<Empty>> replaceServerConfiguration(@Body ServerConfigurationPut put);

      @PATCH("/1.0")
      public Call<LxdResponse<Empty>> updateServerConfiguration(@Body ServerConfigurationPut put);

}
