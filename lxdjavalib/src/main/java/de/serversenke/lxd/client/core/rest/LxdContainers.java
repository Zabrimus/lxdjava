package de.serversenke.lxd.client.core.rest;

import java.util.concurrent.CompletableFuture;

import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.Container;
import de.serversenke.lxd.client.core.model.ContainerExecPost;
import de.serversenke.lxd.client.core.model.ContainerMetadata;
import de.serversenke.lxd.client.core.model.ContainerPost;
import de.serversenke.lxd.client.core.model.ContainerPostMigrationResponse;
import de.serversenke.lxd.client.core.model.ContainerPut;
import de.serversenke.lxd.client.core.model.ContainerSnapshotName;
import de.serversenke.lxd.client.core.model.ContainerSnapshotPost;
import de.serversenke.lxd.client.core.model.ContainerSnapshotRestore;
import de.serversenke.lxd.client.core.model.ContainerSnapshotsPost;
import de.serversenke.lxd.client.core.model.ContainerState;
import de.serversenke.lxd.client.core.model.ContainerStatePut;
import de.serversenke.lxd.client.core.model.ContainersPost;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LxdContainers {

      @GET("/1.0/containers")
      public Call<LxdResponse<String[]>> list();

      @POST("/1.0/containers")
      public Call<LxdResponse<BackgroundOperation>> create(@Body ContainersPost container);

      @GET("/1.0/containers/{name}")
      public Call<LxdResponse<Container>> get(@Path("name") String name);

      @PUT("/1.0/containers/{name}")
      public Call<LxdResponse<BackgroundOperation>> replaceConfiguration(@Path("name") String name, @Body ContainerPut configuration);

      @PATCH("/1.0/containers/{name}")
      public Call<LxdResponse<Empty>> updateConfiguration(@Path("name") String name, @Body ContainerPut configuration);

      @POST("/1.0/containers/{name}")
      public Call<LxdResponse<BackgroundOperation>> rename(@Path("name") String name, @Body ContainerPost configuration);

      @POST("/1.0/containers/{name}")
      public Call<LxdResponse<ContainerPostMigrationResponse>> migrate(@Path("name") String name, @Body ContainerPost configuration);

      @DELETE("/1.0/containers/{name}")
      public Call<LxdResponse<BackgroundOperation>> delete(@Path("name") String name);

      @PUT("/1.0/containers/{name}")
      public Call<LxdResponse<BackgroundOperation>> restoreSnapshot(@Path("name") String name, @Body ContainerSnapshotRestore restore);

      @GET("/1.0/containers/{name}/console")
      public Call<LxdResponse<String[]>> getConsole(@Path("name") String name);

      @DELETE("/1.0/containers/{name}/console")
      public Call<LxdResponse<Empty>> clearConsole(@Path("name") String name);

      @POST("1.0/containers/{name}/exec")
      public Call<LxdResponse<BackgroundOperation>> execCommand(@Path("name") String container, @Body ContainerExecPost command);

      @GET("/1.0/containers/{name}/files")
      public CompletableFuture<Response<ResponseBody>> getFile(@Path("name") String container, @Query("path") String pathToContainerFile);

      @POST("/1.0/containers/{name}/files")
      public CompletableFuture<LxdResponse<Empty>> putFile(@Path("name") String container, @Query("path") String pathToContainerFile, @Body RequestBody filePart, @Header("X-LXD-uid") int uid, @Header("X-LXD-gid") int gid, @Header("X-LXD-mode") String mode, @Header("X-LXD-type") String type, @Header("X-LXD-write") String write);

      @POST("/1.0/containers/{name}/files")
      public CompletableFuture<LxdResponse<Empty>> mkdir(@Path("name") String container, @Query("path") String pathToContainerDir, @Header("X-LXD-uid") int uid, @Header("X-LXD-gid") int gid, @Header("X-LXD-mode") String mode, @Header("X-LXD-type") String type, @Header("X-LXD-write") String write);

      @POST("/1.0/containers/{name}/files")
      public CompletableFuture<LxdResponse<Empty>> mklink(@Path("name") String container, @Query("path") String dest, @Body RequestBody source, @Header("X-LXD-uid") int uid, @Header("X-LXD-gid") int gid, @Header("X-LXD-mode") String mode, @Header("X-LXD-type") String type, @Header("X-LXD-write") String write);

      @POST("/1.0/containers/{name}/files")
      public CompletableFuture<LxdResponse<Empty>> createLink(@Path("name") String container, @Query("path") String linkFrom, @Query("Path2") String linkTo, @Header("X-LXD-uid") int uid, @Header("X-LXD-gid") int gid, @Header("X-LXD-mode") String mode, @Header("X-LXD-type") String type, @Header("X-LXD-write") String write);

      @DELETE("/1.0/containers/{name}/files")
      public Call<LxdResponse<Empty>> deleteFile(@Path("name") String container, @Query("path") String pathToContainerFile);

      @GET("/1.0/containers/{name}/snapshots")
      public Call<LxdResponse<String[]>> listSnapshots(@Path("name") String name);

      @POST("/1.0/containers/{name}/snapshots")
      public Call<LxdResponse<String[]>> createSnapshot(@Path("name") String name, @Body ContainerSnapshotsPost snapshot);

      @GET("/1.0/containers/{name}/snapshots/{sname}")
      public Call<LxdResponse<ContainerSnapshotName>> getSnapshot(@Path("name") String name, @Path("sname") String snapshotName);

      @POST("/1.0/containers/{name}/snapshots/{sname}")
      public Call<LxdResponse<BackgroundOperation>> renameSnapshot(@Path("name") String name, @Path("sname") String snapshotName, @Body ContainerSnapshotPost newName);

      @POST("/1.0/containers/{name}/snapshots/{sname}")
      public Call<LxdResponse<BackgroundOperation>> migrateSnapshot(@Path("name") String name, @Path("sname") String snapshotName, @Body ContainerSnapshotPost newName);

      @DELETE("/1.0/containers/{name}/snapshots/{sname}")
      public Call<LxdResponse<Empty>> deleteSnapshot(@Path("name") String name, @Path("sname") String snapshotName);

      @GET("/1.0/containers/{name}/state")
      public Call<LxdResponse<ContainerState>> getState(@Path("name") String name);

      @PUT("/1.0/containers/{name}/state")
      public Call<LxdResponse<BackgroundOperation>> changeState(@Path("name") String name, @Body ContainerStatePut newState);

      @GET("/1.0/containers/{name}/logs")
      public Call<LxdResponse<String[]>> listLogFiles(@Path("name") String name);

      @GET("/1.0/containers/{name}/logs/{logfile}")
      public Call<LxdResponse<String[]>> getLogFile(@Path("name") String name, @Path("logfile") String logFile);

      @DELETE("/1.0/containers/{name}/logs/{logfile}")
      public Call<LxdResponse<Empty>> deleteLogFile(@Path("name") String name, @Path("logfile") String logFile);

      @GET("/1.0/containers/{name}/metadata")
      public Call<LxdResponse<ContainerMetadata>> getMetadata(@Path("name") String name);

      @PUT("/1.0/containers/{name}/metadata")
      public Call<LxdResponse<Empty>> replaceMetadata(@Path("name") String name, @Body ContainerMetadata newMetadata);

      @GET("/1.0/containers/{name}/metadata/templates")
      public Call<LxdResponse<String[]>> listMetadataTemplates(@Path("name") String name);

      @GET("/1.0/containers/{name}/metadata/templates")
      public Call<LxdResponse<String[]>> getMetadataTemplate(@Path("name") String name, @Query("path") String template);

      @POST("/1.0/containers/{name}/metadata/templates")
      public CompletableFuture<LxdResponse<Empty>> addMetadataTemplate(@Path("name") String container, @Query("path") String template, @Body RequestBody filePart);

      @PUT("/1.0/containers/{name}/metadata/templates")
      public CompletableFuture<LxdResponse<Empty>> replaceMetadataTemplate(@Path("name") String name, @Query("path") String template, @Body RequestBody filePart);

      @DELETE("/1.0/containers/{name}/metadata/templates")
      public Call<LxdResponse<Empty>> deleteMetadataTemplate(@Path("name") String name, @Query("path") String template);
}

