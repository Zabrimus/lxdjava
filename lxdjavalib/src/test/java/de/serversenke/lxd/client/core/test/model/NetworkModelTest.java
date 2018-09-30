package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.Network;
import de.serversenke.lxd.client.core.model.NetworkPost;
import de.serversenke.lxd.client.core.model.NetworkPostRename;
import de.serversenke.lxd.client.core.model.NetworkPut;

public class NetworkModelTest extends TestBase {

    @Test
    void testNetworkPostDeSerialize() {
        testDeSerialize("request_network_post.json", NetworkPost.class);
    }

    @Test
    void testNetworkNameGetDeSerialize() {
        testDeSerialize("response_network_name_get.json", Network.class);
    }

    @Test
    void testNetworkNamePutDeSerialize() {
        testDeSerialize("request_network_name_put.json", NetworkPut.class);
    }

    @Test
    void testNetworkNamePatchDeSerialize() {
        testDeSerialize("request_network_name_patch.json", NetworkPut.class);
    }

    @Test
    void testNetworkNameRenameDeSerialize() {
        testDeSerialize("request_network_name_rename.json", NetworkPostRename.class);
    }

}
