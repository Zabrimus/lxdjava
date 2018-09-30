package de.serversenke.lxd.client.core;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.serversenke.lxd.client.core.model.InstantConverter;
import de.serversenke.lxd.client.core.model.LxdResponse;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
public class ApiBase {
    private static Map<String, Retrofit> retrofit = new ConcurrentHashMap<String, Retrofit>();

    private static OkHttpClient okhttpClient;
    private static Gson gson;

    public ApiBase() {
        createClient();
        createGson();
    }

    private class HttpLoggingInterceptorLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            log.debug(message);
        }
    }

    protected Retrofit getRetrofit(String url) {
        Gson gson = createGson();
        OkHttpClient client = createClient();

        Retrofit r = retrofit.get(url);
        if (r == null) {
            r = new Retrofit.Builder() //
                    .baseUrl(url) //
                    .client(client) //
                    .addCallAdapterFactory(Java8CallAdapterFactory.create()) //
                    .addConverterFactory(GsonConverterFactory.create(gson)) //
                    .build();

            retrofit.put(url, r);
        }

        return r;
    }

    @Synchronized
    protected Gson createGson() {
        if (gson != null) {
            return gson;
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<Instant>(){}.getType(), new InstantConverter());
        // builder.serializeNulls();
        gson = builder.create();

        return gson;
    }

    @Synchronized
    protected OkHttpClient createClient() {
        if (okhttpClient != null) {
            return okhttpClient;
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptorLogger());
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Builder builder = new OkHttpClient.Builder()//
                .connectTimeout(10, TimeUnit.SECONDS) //
                .writeTimeout(0, TimeUnit.SECONDS) //
                .readTimeout(0, TimeUnit.SECONDS); //

        // builder = builder.addInterceptor(interceptor);

        builder = ClientKeyStore.addSecurity(builder);


        okhttpClient = builder.build();

        return okhttpClient;
    }

    protected <T> ClientResponse<T> execute(Call<LxdResponse<T>> request) {
        try {
            Response<LxdResponse<T>> resp = request.execute();
            if (resp.body() != null) {
                return new ClientResponse<T>(resp.headers(), resp.code(), resp.body().getMetadata(), resp.body().getResponseType(), resp.body().getStatus(), resp.body().getOperation(), resp.body().getErrorCode(), resp.body().getErrorMessage());
            } else {
                return new ClientResponse<T>(resp.headers(), resp.code(), null, "error", null, null, resp.code(), resp.message());
            }
        } catch (IOException e) {
            log.info("Error", e);
            return new ClientResponse<T>(null, 400, null, ClientResponse.ResponseType.ERROR.toString(), null, null, 400, e.getMessage());
        }
    }
}
