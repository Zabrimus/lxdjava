package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.ClusterDisable;
import de.serversenke.lxd.client.core.model.ClusterInfo;
import de.serversenke.lxd.client.core.model.ClusterJoin;
import de.serversenke.lxd.client.core.model.ClusterMember;
import de.serversenke.lxd.client.core.model.ClusterRename;

public class ClusterModelTest extends TestBase {

    @Test
    void testCluserInfoDeSerialize() {
        testDeSerialize("response_cluster_info.json", ClusterInfo.class);
    }

    @Test
    void testClusterDisabletDeSerialize() {
        testDeSerialize("request_cluster_disable.json", ClusterDisable.class);
    }

    @Test
    void testClusterInfoPutDeSerialize() {
        testDeSerialize("request_cluster_info_put.json", ClusterInfo.class);
    }

    @Test
    void testClusterJoinDeSerialize() {
        testDeSerialize("request_cluster_join.json", ClusterJoin.class);
    }

    @Test
    void testClusterMemberRenameDeSerialize() {
        testDeSerialize("request_cluster_member_rename.json", ClusterRename.class);
    }

    @Test
    void testClusterMemberDeSerialize() {
        testDeSerialize("response_cluster_member.json", ClusterMember.class);
    }
}
