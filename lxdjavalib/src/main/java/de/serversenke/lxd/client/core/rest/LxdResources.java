package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.SystemResources;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LxdResources {

      @GET("/1.0/resources")
      public Call<LxdResponse<SystemResources>> get();
}
