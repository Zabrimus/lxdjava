package de.serversenke.lxd.client.core.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.Server;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.ServerConfiguration;
import de.serversenke.lxd.client.core.model.ServerConfigurationPut;
import de.serversenke.lxd.client.core.model.StatusCode;
import retrofit2.Call;

public class ServerTest extends TestBase {

    class MyServer<TT> extends Server {

        private Call<LxdResponse<TT>> request;

        public MyServer(Call<LxdResponse<TT>> req, String url) throws Exception {
            super(url);
            request = req;
        }

        @SuppressWarnings("unchecked")
        protected <T> ClientResponse<T> execute(Call<LxdResponse<T>> dummy) {
            return (ClientResponse<T>) super.execute((Call<LxdResponse<TT>>) request);
        }
    }


    @Test
    void testServerApiRest() {
        String[] responseObject = createObject("response_server_api.json", String[].class);
        Call<LxdResponse<String[]>> call = createCall(responseObject);

        ClientResponse<String[]> response = null;
        try {
            response = new MyServer<String[]>(call, "http://127.0.0.1").listSupportedApi();
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testServerApiConfiguration() {
        ServerConfiguration responseObject = createObject("response_server_configuration.json", ServerConfiguration.class);
        Call<LxdResponse<ServerConfiguration>> call = createCall(responseObject);

        ClientResponse<ServerConfiguration> response = null;
        try {
            response = new MyServer<ServerConfiguration>(call, "http://127.0.0.1").getServerConfiguration();
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testServerPut() {
        ServerConfigurationPut request = createObject("request_server_put.json", ServerConfigurationPut.class);
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").replaceServerConfiguration(request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

    @Test
    void testServerPatch() {
        ServerConfigurationPut request = createObject("request_server_patch.json", ServerConfigurationPut.class);
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").updateServerConfiguration(request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }
}
