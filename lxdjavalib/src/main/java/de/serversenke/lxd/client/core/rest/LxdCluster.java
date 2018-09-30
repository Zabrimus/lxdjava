package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.ClusterDisable;
import de.serversenke.lxd.client.core.model.ClusterInfo;
import de.serversenke.lxd.client.core.model.ClusterJoin;
import de.serversenke.lxd.client.core.model.ClusterMember;
import de.serversenke.lxd.client.core.model.ClusterRename;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LxdCluster {

      @GET("/1.0/cluster")
      public Call<LxdResponse<ClusterInfo>> get();

      @PUT("/1.0/cluster")
      public Call<LxdResponse<BackgroundOperation>> create(@Body ClusterInfo cluster);

      @PUT("/1.0/cluster")
      public Call<LxdResponse<BackgroundOperation>> join(@Body ClusterJoin cluster);

      @PUT("/1.0/cluster")
      public Call<LxdResponse<BackgroundOperation>> disable(@Body ClusterDisable cluster);

      @GET("/1.0/cluster/members")
      public Call<LxdResponse<String[]>> members();

      @GET("/1.0/cluster/members/{name}")
      public Call<LxdResponse<ClusterMember>> getMember(@Path("name") String name);

      @POST("/1.0/cluster/members/{name}")
      public Call<LxdResponse<Empty>> renameMember(@Path("name") String name, @Body ClusterRename newName);

      @DELETE("/1.0/cluster/members/{name}")
      public Call<LxdResponse<Empty>> removeMember(@Path("name") String name);

      @DELETE("/1.0/cluster/members/{name}?force=1")
      public Call<LxdResponse<Empty>> removeMemberForced(@Path("name") String name);
}


