package de.serversenke.lxd.client.core.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.InstantConverter;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.test.util.TestData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

public class TestBase {

    protected Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<Instant>(){}.getType(), new InstantConverter());
        builder.registerTypeAdapterFactory(new ValidatorAdapterFactory());
        builder.serializeNulls();

        Gson gson = builder.create();
        return gson;
    }

    protected String getJson(String filename) {
        String json = null;
        try {
            json = TestData.read(filename);
        } catch (Exception e) {
            assertTrue(false, "Test file not found");
        }

        return json;
    }

    <T> void testDeSerialize(String filename, Class<T> clazz) {
        String json = getJson(filename);
        Gson gson = getGson();

        T target1;
        T target2;
        String output1;
        String output2;

        // 1. (de)serialization
        target1 = gson.fromJson(json, clazz);
        output1 = gson.toJson(target1);

        // 2. (de)serialization
        target2 = gson.fromJson(output1, clazz);
        output2 = gson.toJson(target2);

        assertEquals(output1, output2, clazz.getName() + " (de)serialization failed");

        if (target1 instanceof String[]) {
            String[] arr1 = (String[]) target1;
            String[] arr2 = (String[]) target2;

            for (int i = 0; i < arr1.length; ++i) {
                assertEquals(arr1[i], arr2[i]);
            }
        } else {
            assertEquals(target1, target2, clazz.getName() + " (de)serialization failed");
        }
    }

    protected <T> T createObject(String filename, Class<T> clazz) {
        return getGson().fromJson(getJson(filename), clazz);
    }

    protected <T> Call<LxdResponse<T>> createCall(T responseObject) {
        LxdResponse<T> resp = new LxdResponse<T>();
        resp.setMetadata(responseObject);

        Response<LxdResponse<T>> response = Response.success(resp);
        Call<LxdResponse<T>> call = Calls.response(response);

        return call;
    }

    protected Call<LxdResponse<Empty>> createEmptyCall() {
        LxdResponse<Empty> resp = new LxdResponse<Empty>();
        resp.setMetadata(new Empty());

        Response<LxdResponse<Empty>> response = Response.success(resp);
        Call<LxdResponse<Empty>> call = Calls.response(response);

        return call;
    }

}
