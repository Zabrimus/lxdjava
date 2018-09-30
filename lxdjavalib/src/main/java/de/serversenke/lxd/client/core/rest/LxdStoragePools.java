package de.serversenke.lxd.client.core.rest;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.StoragePool;
import de.serversenke.lxd.client.core.model.StoragePoolPost;
import de.serversenke.lxd.client.core.model.StoragePoolPut;
import de.serversenke.lxd.client.core.model.StoragePoolResources;
import de.serversenke.lxd.client.core.model.StoragePoolVolume;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeConfig;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeMigration;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeRename;
import de.serversenke.lxd.client.core.model.StoragePoolVolumesPost;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LxdStoragePools {

      @GET("/1.0/storage-pools")
      public Call<LxdResponse<String[]>> list();

      @POST("/1.0/storage-pools")
      public Call<LxdResponse<Empty>> create(@Body StoragePoolPost newPool);

      @GET("/1.0/storage-pools/{name}")
      public Call<LxdResponse<StoragePool>> get(@Path("name") String name);

      @PUT("/1.0/storage-pools/{name}")
      public Call<LxdResponse<Empty>> replace(@Path("name") String name, @Body StoragePoolPut replacement);

      @PATCH("/1.0/storage-pools/{name}")
      public Call<LxdResponse<Empty>> update(@Path("name") String name, @Body StoragePoolPut replacement);

      @DELETE("/1.0/storage-pools/{name}")
      public Call<LxdResponse<Empty>> delete(@Path("name") String name);

      @GET("/1.0/storage-pools/{name}/resources")
      public Call<LxdResponse<StoragePoolResources>> getResources(@Path("name") String name);

      @GET("/1.0/storage-pools/{name}/volumes")
      public Call<LxdResponse<String[]>> getVolumes(@Path("name") String name);

      @POST("/1.0/storage-pools/{name}/volumes")
      public Call<LxdResponse<Empty>> createVolume(@Path("name") String name, @Body StoragePoolVolumesPost volume);

      @POST("/1.0/storage-pools/{name}/volumes")
      public Call<LxdResponse<Empty>> copyVolume(@Path("name") String name, @Body StoragePoolVolumesPost volume);

      @POST("/1.0/storage-pools/{name}/volumes")
      public Call<LxdResponse<Empty>> migrateVolume(@Path("name") String name, @Body StoragePoolVolumesPost volume);

      @POST("/1.0/storage-pools/{name}/volumes/{type}/{volume}")
      public Call<LxdResponse<StoragePoolVolumeMigration>> renameVolume(@Path("name") String name, @Path("type") String type, @Path("volume") String volume, @Body StoragePoolVolumeRename volumeRename);

      @GET("/1.0/storage-pools/{name}/volumes/{type}/{volume}")
      public Call<LxdResponse<StoragePoolVolume>> getVolume(@Path("name") String name, @Path("type") String type, @Path("volume") String volume);

      @POST("/1.0/storage-pools/{name}/volumes/{type}/{volume}")
      public Call<LxdResponse<Empty>> replaceVolumeConfig(@Path("name") String name, @Path("type") String type, @Path("volume") String volume, @Body StoragePoolVolumeConfig config);

      @POST("/1.0/storage-pools/{name}/volumes/{type}/{volume}")
      public Call<LxdResponse<Empty>> updateVolumeConfig(@Path("name") String name, @Path("type") String type, @Path("volume") String volume, @Body StoragePoolVolumeConfig config);

      @POST("/1.0/storage-pools/{name}/volumes/{type}/{volume}")
      public Call<LxdResponse<Empty>> deleteVolume(@Path("name") String name, @Path("type") String type, @Path("volume") String volume);
}
