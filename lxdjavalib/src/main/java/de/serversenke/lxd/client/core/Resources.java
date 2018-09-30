package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.SystemResources;
import de.serversenke.lxd.client.core.rest.LxdResources;
import lombok.SneakyThrows;

public class Resources extends ApiBase {

    private LxdResources lxdResources;

    @SneakyThrows
    public Resources(String url) {
        lxdResources = getRetrofit(url).create(LxdResources.class);
    }

    /**
     * GET /1.0/resources
     */
    public ClientResponse<SystemResources> get() {
        return execute(lxdResources.get());
    }
}
