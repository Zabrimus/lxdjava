package de.serversenke.lxd.client.core.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.Cluster;
import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.ClusterDisable;
import de.serversenke.lxd.client.core.model.ClusterInfo;
import de.serversenke.lxd.client.core.model.ClusterJoin;
import de.serversenke.lxd.client.core.model.ClusterMember;
import de.serversenke.lxd.client.core.model.ClusterRename;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.StatusCode;
import retrofit2.Call;

public class ClusterTest extends TestBase {

    class MyServer<TT> extends Cluster {

        private Call<LxdResponse<TT>> request;

        public MyServer(Call<LxdResponse<TT>> req, String url) throws Exception {
            super(url);
            request = req;
        }

        @SuppressWarnings("unchecked")
        protected <T> ClientResponse<T> execute(Call<LxdResponse<T>> dummy) {
            return (ClientResponse<T>) super.execute((Call<LxdResponse<TT>>) request);
        }
    }

    @Test
    void testClusterGet() {
        ClusterInfo responseObject = createObject("response_cluster_info.json", ClusterInfo.class);
        Call<LxdResponse<ClusterInfo>> call = createCall(responseObject);

        ClientResponse<ClusterInfo> response = null;
        try {
            response = new MyServer<ClusterInfo>(call, "http://127.0.0.1").get();
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testClusterCreate() {
        ClusterInfo request = createObject("response_cluster_info.json", ClusterInfo.class);
        BackgroundOperation responseObject = createObject("background_operation.json", BackgroundOperation.class);

        Call<LxdResponse<BackgroundOperation>> call = createCall(responseObject);

        ClientResponse<BackgroundOperation> response = null;
        try {
            response = new MyServer<BackgroundOperation>(call, "http://127.0.0.1").create(request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testClusterJoin() {
        ClusterJoin request = createObject("request_cluster_join.json", ClusterJoin.class);
        BackgroundOperation responseObject = createObject("background_operation.json", BackgroundOperation.class);

        Call<LxdResponse<BackgroundOperation>> call = createCall(responseObject);

        ClientResponse<BackgroundOperation> response = null;
        try {
            response = new MyServer<BackgroundOperation>(call, "http://127.0.0.1").join(request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testClusterDisable() {
        ClusterDisable request = createObject("request_cluster_disable.json", ClusterDisable.class);
        BackgroundOperation responseObject = createObject("background_operation.json", BackgroundOperation.class);

        Call<LxdResponse<BackgroundOperation>> call = createCall(responseObject);

        ClientResponse<BackgroundOperation> response = null;
        try {
            response = new MyServer<BackgroundOperation>(call, "http://127.0.0.1").disable(request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testClusterMembers() {
        String[] responseObject = createObject("response_cluster_members.json", String[].class);

        Call<LxdResponse<String[]>> call = createCall(responseObject);

        ClientResponse<String[]> response = null;
        try {
            response = new MyServer<String[]>(call, "http://127.0.0.1").members();
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testClusterMember() {
        ClusterMember responseObject = createObject("response_cluster_member.json", ClusterMember.class);

        Call<LxdResponse<ClusterMember>> call = createCall(responseObject);

        ClientResponse<ClusterMember> response = null;
        try {
            response = new MyServer<ClusterMember>(call, "http://127.0.0.1").getMember("name");
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertEquals(responseObject, response.getResponse(), "wrong result");
    }

    @Test
    void testClusterRenameMember() {
        ClusterRename request = createObject("request_cluster_member_rename.json", ClusterRename.class);
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").renameMember("old", request);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

    @Test
    void testClusterRemoveMember() {
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").removeMember("name");
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

    @Test
    void testClusterRemoveMemberForced() {
        Call<LxdResponse<Empty>> call = createEmptyCall();

        ClientResponse<Empty> response = null;
        try {
            response = new MyServer<Empty>(call, "http://127.0.0.1").removeMemberForced("name");
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(response.getStatusCode(), StatusCode.Success, "wrong status code");
        assertTrue(response.getResponse() instanceof Empty);
    }

}
