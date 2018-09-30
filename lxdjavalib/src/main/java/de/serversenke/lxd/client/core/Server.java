package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.ServerConfiguration;
import de.serversenke.lxd.client.core.model.ServerConfigurationPut;
import de.serversenke.lxd.client.core.rest.LxdServer;
import lombok.SneakyThrows;


public class Server extends ApiBase {

    private LxdServer lxdServer;

    @SneakyThrows
    public Server(String url) {
        lxdServer = getRetrofit(url).create(LxdServer.class);
    }

    /**
     * GET /
     */
    public ClientResponse<String[]> listSupportedApi() {
        return execute(lxdServer.listSupportedApi());
    }

    /**
     * GET /1.0
     */
    public ClientResponse<ServerConfiguration> getServerConfiguration() {
        return execute(lxdServer.getServer());
    }

    /**
     * PUT /1.0
     */
    public ClientResponse<Empty> replaceServerConfiguration(ServerConfigurationPut put) {
        return execute(lxdServer.replaceServerConfiguration(put));
    }

    /**
     * PATCH /1.0
     */
    public ClientResponse<Empty> updateServerConfiguration(ServerConfigurationPut put) {
        return execute(lxdServer.updateServerConfiguration(put));
    }
}
