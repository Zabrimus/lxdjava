package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.ProfilePost;
import de.serversenke.lxd.client.core.model.ProfilePostRename;
import de.serversenke.lxd.client.core.model.ProfilePut;
import de.serversenke.lxd.client.core.rest.LxdProfiles;
import lombok.SneakyThrows;

public class Profiles extends ApiBase {
    private LxdProfiles lxdProfiles;

    @SneakyThrows
    public Profiles(String url) {
        lxdProfiles = getRetrofit(url).create(LxdProfiles.class);
    }

    /**
     * GET /1.0/profiles
     */
    public ClientResponse<String[]> list() {
        return execute(lxdProfiles.list());
    }

    /**
     * POST /1.0/profiles
     */
    public ClientResponse<Empty> create(ProfilePost newProfile) {
        return execute(lxdProfiles.create(newProfile));
    }

    /**
     * GET /1.0/profiles/{name}
     */
    public ClientResponse<Empty> get(String name) {
        return execute(lxdProfiles.get(name));
    }

    /**
     * PUT /1.0/profiles/{name}
     */
    public ClientResponse<Empty> replace(String name, ProfilePut replacement) {
        return execute(lxdProfiles.replace(name, replacement));
    }

    /**
     * PATCH /1.0/profiles/{name}
     */
    public ClientResponse<Empty> update(String name, ProfilePut replacement) {
        return execute(lxdProfiles.update(name, replacement));
    }

    /**
     * POST /1.0/profiles/{name}
     */
    public ClientResponse<Empty> rename(String name, ProfilePostRename newName) {
        return execute(lxdProfiles.rename(name, newName));
    }

    /**
     * DELETE /1.0/profiles/{name}
     */
    public ClientResponse<Empty> delete(String name) {
        return execute(lxdProfiles.delete(name));
    }
}
