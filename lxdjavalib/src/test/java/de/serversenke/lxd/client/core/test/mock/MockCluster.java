package de.serversenke.lxd.client.core.test.mock;

import java.util.concurrent.TimeUnit;

import de.serversenke.lxd.client.core.Cluster;
import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.ClusterDisable;
import de.serversenke.lxd.client.core.model.ClusterInfo;
import de.serversenke.lxd.client.core.model.ClusterJoin;
import de.serversenke.lxd.client.core.model.ClusterMember;
import de.serversenke.lxd.client.core.model.ClusterRename;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.rest.LxdCluster;
import de.serversenke.lxd.client.core.test.util.TestData;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class MockCluster extends Cluster {
	
	static final class MockLxdCluster implements LxdCluster {
		private final BehaviorDelegate<LxdCluster> delegate;
		
		public MockLxdCluster(BehaviorDelegate<LxdCluster> delegate) {
			this.delegate = delegate;
		}

		@Override
		public Call<LxdResponse<ClusterInfo>> get() {
			ClusterInfo metadata = new ClusterInfo();
			metadata.setEnabled(true);
			metadata.setServerName("servername");
			
			LxdResponse<ClusterInfo> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);			
			return delegate.returningResponse(response).get();
		}

		@Override
		public Call<LxdResponse<BackgroundOperation>> create(ClusterInfo cluster) {			
			LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());			
			return delegate.returningResponse(response).create(cluster);
		}

		@Override
		public Call<LxdResponse<BackgroundOperation>> join(ClusterJoin cluster) {
			LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());			
			return delegate.returningResponse(response).join(cluster);
		}

		@Override
		public Call<LxdResponse<BackgroundOperation>> disable(ClusterDisable cluster) {
			LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());			
			return delegate.returningResponse(response).disable(cluster);
		}

		@Override
		public Call<LxdResponse<String[]>> members() {
			LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"member 1", "member 2"});			
			return delegate.returningResponse(response).members();
		}

		@Override
		public Call<LxdResponse<ClusterMember>> getMember(String name) {
			ClusterMember metadata = new ClusterMember();
			metadata.setDatabase(true);
			metadata.setMessage("message");
			metadata.setName("name");
			metadata.setState("state");
			metadata.setUrl("url");
			
			LxdResponse<ClusterMember> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);
			
			return delegate.returningResponse(response).getMember(name);
		}

		@Override
		public Call<LxdResponse<Empty>> renameMember(String name, ClusterRename newName) {
			LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).renameMember(name, newName);
		}

		@Override
		public Call<LxdResponse<Empty>> removeMember(String name) {
			LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).removeMember(name);
		}

		@Override
		public Call<LxdResponse<Empty>> removeMemberForced(String name) {
			LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).removeMemberForced(name);
		}
	}

	public MockCluster(String url) throws Exception {
		super(url);

		NetworkBehavior behavior = NetworkBehavior.create();
		behavior.setErrorPercent(0);
		behavior.setDelay(0, TimeUnit.SECONDS);
		
		MockRetrofit mockRetrofit = new MockRetrofit.Builder(getRetrofit(url)).networkBehavior(behavior).build();

		BehaviorDelegate<LxdCluster> delegate = mockRetrofit.create(LxdCluster.class);
		MockLxdCluster mockLxdCluster = new MockLxdCluster(delegate);
		
		lxdCluster = mockLxdCluster;
	}
}
