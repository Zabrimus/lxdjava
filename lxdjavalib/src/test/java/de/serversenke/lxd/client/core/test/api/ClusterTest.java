package de.serversenke.lxd.client.core.test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.ClusterDisable;
import de.serversenke.lxd.client.core.model.ClusterInfo;
import de.serversenke.lxd.client.core.model.ClusterJoin;
import de.serversenke.lxd.client.core.model.ClusterMember;
import de.serversenke.lxd.client.core.model.ClusterRename;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.test.mock.MockCluster;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClusterTest extends TestBase {
	
	private MockCluster cluster;
	
	@BeforeAll
	void beforeAll() {
		try {
			cluster = new MockCluster("http://localhost");
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
    public void get() {
		ClientResponse<ClusterInfo> response = cluster.get();

		testCommonResponse(response);
    	assertEquals(response.getResponse().getServerName(), "servername");
    }

	@Test
    public void create() {
		ClusterInfo metadata = new ClusterInfo();
		metadata.setEnabled(true);
		metadata.setServerName("server");
		
		ClientResponse<BackgroundOperation> response = cluster.create(metadata);
		testCommonResponse(response);
    }

	@Test
    public void join() {
		ClusterJoin metadata = new ClusterJoin();
		metadata.setClusterAddress("address");
		metadata.setClusterCertificate("certificate");
		metadata.setEnabled(true);
		metadata.setServerName("name");
		
		ClientResponse<BackgroundOperation> response = cluster.join(metadata);
		testCommonResponse(response);
    }

	@Test
    public void disable() {
		ClusterDisable metadata = new ClusterDisable();
		metadata.setEnabled(false);
		metadata.setServerName("name");
		
		ClientResponse<BackgroundOperation> response = cluster.disable(metadata);
		testCommonResponse(response);
    }

	@Test
    public void members() {
		ClientResponse<String[]> response = cluster.members();
		testCommonResponse(response);
    }

	@Test
    public void getMember() {
		ClientResponse<ClusterMember> response = cluster.getMember("name");
		testCommonResponse(response);
    }

	@Test
    public void renameMember() {
		ClusterRename rename = new ClusterRename();
		rename.setServerName("new");
		
		ClientResponse<Empty> response = cluster.renameMember("old", rename);
		testCommonResponse(response);
    }

    @Test
    public void removeMember() {
    	ClientResponse<Empty> response = cluster.removeMember("name");
    	testCommonResponse(response);
    }

    @Test
    public void removeMemberForced() {
    	ClientResponse<Empty> response = cluster.removeMemberForced("name");
    	testCommonResponse(response);
    }
}
