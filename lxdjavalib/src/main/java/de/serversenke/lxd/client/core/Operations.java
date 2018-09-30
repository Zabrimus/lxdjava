package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.Operation;
import de.serversenke.lxd.client.core.rest.LxdOperations;
import lombok.SneakyThrows;

public class Operations extends ApiBase {
    private LxdOperations lxdOperations;

    @SneakyThrows
    public Operations(String url) {
        lxdOperations = getRetrofit(url).create(LxdOperations.class);
    }

    /**
     * GET /1.0/operations
     */
    public ClientResponse<String[]> list() {
        return execute(lxdOperations.list());
    }

    /**
     * GET /1.0/operations/{uuid}
     */
    public ClientResponse<Operation> get(String uuid) {
        return execute(lxdOperations.get(uuid));
    }

    /**
     * DELETE /1.0/operations/{uuid}
     */
    public ClientResponse<Empty> cancel(String uuid) {
        return execute(lxdOperations.cancel(uuid));
    }

    /**
     * GET /1.0/operations/{uuid}/wait
     */
    public ClientResponse<Operation> wait(String uuid) {
        return execute(lxdOperations.wait(uuid));
    }

    /**
     * GET /1.0/operations/{uuid}/wait
     */
    public ClientResponse<Operation> waitTimeout(String uuid, int timeout) {
        return execute(lxdOperations.waitTimeout(uuid, timeout));
    }
}
