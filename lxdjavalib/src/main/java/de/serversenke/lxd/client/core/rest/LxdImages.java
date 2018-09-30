package de.serversenke.lxd.client.core.rest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.ImageAliasGet;
import de.serversenke.lxd.client.core.model.ImageAliasPatch;
import de.serversenke.lxd.client.core.model.ImageAliasPost;
import de.serversenke.lxd.client.core.model.ImageAliasPostRename;
import de.serversenke.lxd.client.core.model.ImageAliasPut;
import de.serversenke.lxd.client.core.model.ImageGet;
import de.serversenke.lxd.client.core.model.ImagePut;
import de.serversenke.lxd.client.core.model.ImagesPost;
import de.serversenke.lxd.client.core.model.LxdResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LxdImages {

      @GET("/1.0/images")
      public Call<LxdResponse<String[]>> list();

      @POST("/1.0/images")
      public CompletableFuture<LxdResponse<Empty>> uploadImage(@Body RequestBody filePart);

      @POST("/1.0/images")
      public Call<LxdResponse<BackgroundOperation>> createImage(@Body ImagesPost imagePost);

      @GET("/1.0/images/{fingerprint}")
      public Call<LxdResponse<ImageGet>> get(@Path("fingerprint") String fingerprint, @Query("secret") String secret);

      @PUT("/1.0/images/{fingerprint}")
      public Call<LxdResponse<Empty>> replaceProperties(@Path("fingerprint") String fingerprint, @Body ImagePut properties);

      @PATCH("/1.0/images/{fingerprint}")
      public Call<LxdResponse<Empty>> updateProperties(@Path("fingerprint") String fingerprint, @Body ImagePut properties);

      @DELETE("/1.0/images/{fingerprint}")
      public Call<LxdResponse<Empty>> delete(@Path("fingerprint") String fingerprint, @Query("secret") String secret);

      @GET("/1.0/images/{fingerprint}/export")
      public CompletableFuture<Response<ResponseBody>> download(@Path("fingerprint") String fingerprint, @Query("secret") String secret);

      @POST("/1.0/images/{fingerprint}/refresh")
      public Call<LxdResponse<BackgroundOperation>> refresh(@Path("fingerprint") String fingerprint);

      @POST("/1.0/images/<fingerprint>/secret")
      public Call<LxdResponse<Map<String, String>>> createSecretKey(@Path("fingerprint") String fingerprint);

      @GET("/1.0/images/aliases")
      public Call<LxdResponse<String[]>> aliases();

      @POST("/1.0/images/aliases")
      public Call<LxdResponse<String[]>> createAlias(@Body ImageAliasPost newAlias);

      @GET("/1.0/images/aliases/{name}")
      public Call<LxdResponse<ImageAliasGet>> getAlias(@Path("name") String name);

      @PUT("/1.0/images/aliases/{name}")
      public Call<LxdResponse<Empty>> replaceAlias(@Path("name") String name, @Body ImageAliasPut replacement);

      @PATCH("/1.0/images/aliases/{name}")
      public Call<LxdResponse<Empty>> changeAlias(@Path("name") String name, @Body ImageAliasPatch replacement);

      @POST("/1.0/images/aliases/{name}")
      public Call<LxdResponse<Empty>> renameAlias(@Path("name") String name, @Body ImageAliasPostRename replacement);

      @DELETE("/1.0/images/aliases/{name}")
      public Call<LxdResponse<Empty>> deleteAlias(@Path("name") String name);
}
