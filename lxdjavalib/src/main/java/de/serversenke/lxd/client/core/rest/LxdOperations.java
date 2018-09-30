package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.Operation;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LxdOperations {

      @GET("/1.0/operations")
      public Call<LxdResponse<String[]>> list();

      @GET("/1.0/operations/{uuid}")
      public Call<LxdResponse<Operation>> get(@Path("uuid") String uuid);

      @DELETE("/1.0/operations/{uuid}")
      public Call<LxdResponse<Empty>> cancel(@Path("uuid") String uuid);

      @GET("/1.0/operations/{uuid}/wait")
      public Call<LxdResponse<Operation>> wait(@Path("uuid") String uuid);

      @GET("/1.0/operations/{uuid}/wait")
      public Call<LxdResponse<Operation>> waitTimeout(@Path("uuid") String uuid, @Query("timeout") int timeout);
}
