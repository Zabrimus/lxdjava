package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.ClusterDisable;
import de.serversenke.lxd.client.core.model.ClusterInfo;
import de.serversenke.lxd.client.core.model.ClusterJoin;
import de.serversenke.lxd.client.core.model.ClusterMember;
import de.serversenke.lxd.client.core.model.ClusterRename;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.rest.LxdCluster;
import lombok.SneakyThrows;

public class Cluster extends ApiBase {

    protected LxdCluster lxdCluster;

    @SneakyThrows
    public Cluster(String url) {
        lxdCluster = getRetrofit(url).create(LxdCluster.class);
    }

    /**
     * GET /1.0/cluster
     */
    public ClientResponse<ClusterInfo> get() {
        return execute(lxdCluster.get());
    }

    /**
     * PUT /1.0/cluster
     */
    public ClientResponse<BackgroundOperation> create(ClusterInfo cluster) {
        return execute(lxdCluster.create(cluster));
    }

    /**
     * PUT("/1.0/cluster")
     */
    public ClientResponse<BackgroundOperation> join(ClusterJoin cluster) {
        return execute(lxdCluster.join(cluster));
    }

    /**
     * PUT /1.0/cluster
     */
    public ClientResponse<BackgroundOperation> disable(ClusterDisable cluster) {
        return execute(lxdCluster.disable(cluster));
    }

    /**
     * GET /1.0/cluster/members
     */
    public ClientResponse<String[]> members() {
        return execute(lxdCluster.members());
    }

    /**
     * GET /1.0/cluster/members/{name}
     */
    public ClientResponse<ClusterMember> getMember(String name) {
        return execute(lxdCluster.getMember(name));
    }

    /**
     * POST /1.0/cluster/members/{name}
     */
    public ClientResponse<Empty> renameMember(String name, ClusterRename newName) {
        return execute(lxdCluster.renameMember(name, newName));
    }

    /**
     * DELETE /1.0/cluster/members/{name}
     */
    public ClientResponse<Empty> removeMember(String name) {
        return execute(lxdCluster.removeMember(name));
    }

    /**
     * DELETE /1.0/cluster/members/{name}?force=1
     */
    public ClientResponse<Empty> removeMemberForced(String name) {
        return execute(lxdCluster.removeMemberForced(name));
    }
}
