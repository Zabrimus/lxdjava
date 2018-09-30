package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.Network;
import de.serversenke.lxd.client.core.model.NetworkPost;
import de.serversenke.lxd.client.core.model.NetworkPostRename;
import de.serversenke.lxd.client.core.model.NetworkPut;
import de.serversenke.lxd.client.core.rest.LxdNetworks;
import lombok.SneakyThrows;

public class Networks extends ApiBase {

    private LxdNetworks lxdNetworks;

    @SneakyThrows
    public Networks(String url) {
        lxdNetworks = getRetrofit(url).create(LxdNetworks.class);
    }

    /**
     * GET /1.0/networks
     */
    public ClientResponse<String[]> list() {
        return execute(lxdNetworks.list());
    }

    /**
     * POST /1.0/networks
     */
    public ClientResponse<Empty> create(NetworkPost newNetwork) {
        return execute(lxdNetworks.create(newNetwork));
    }

    /**
     * GET /1.0/networks/{name}
     */
    public ClientResponse<Network> get(String name) {
        return execute(lxdNetworks.get(name));
    }

    /**
     * PUT /1.0/networks/{name}
     */
    public ClientResponse<Empty> replace(String name, NetworkPut replacement) {
        return execute(lxdNetworks.replace(name, replacement));
    }

    /**
     * PATCH /1.0/networks/{name}
     */
    public ClientResponse<Empty> update(String name, NetworkPut replacement) {
        return execute(lxdNetworks.update(name, replacement));
    }

    /**
     * PATCH /1.0/networks/{name}
     */
    public ClientResponse<Empty> rename(String name, NetworkPostRename newName) {
        return execute(lxdNetworks.rename(name, newName));
    }

    /**
     * DELETE /1.0/networks/{name}
     */
    public ClientResponse<Empty> delete(String name) {
        return execute(lxdNetworks.delete(name));
    }
}
