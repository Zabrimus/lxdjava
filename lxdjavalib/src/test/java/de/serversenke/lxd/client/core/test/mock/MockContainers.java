package de.serversenke.lxd.client.core.test.mock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import de.serversenke.lxd.client.core.Containers;
import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.Container;
import de.serversenke.lxd.client.core.model.ContainerExecPost;
import de.serversenke.lxd.client.core.model.ContainerMetadata;
import de.serversenke.lxd.client.core.model.ContainerPost;
import de.serversenke.lxd.client.core.model.ContainerPostMigrationResponse;
import de.serversenke.lxd.client.core.model.ContainerPut;
import de.serversenke.lxd.client.core.model.ContainerSnapshotName;
import de.serversenke.lxd.client.core.model.ContainerSnapshotPost;
import de.serversenke.lxd.client.core.model.ContainerSnapshotRestore;
import de.serversenke.lxd.client.core.model.ContainerSnapshotsPost;
import de.serversenke.lxd.client.core.model.ContainerState;
import de.serversenke.lxd.client.core.model.ContainerStatePut;
import de.serversenke.lxd.client.core.model.ContainersPost;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.rest.LxdContainers;
import de.serversenke.lxd.client.core.test.util.TestData;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class MockContainers extends Containers {

    static final class MockLxdContainers implements LxdContainers {
        private final BehaviorDelegate<LxdContainers> delegate;

        public MockLxdContainers(BehaviorDelegate<LxdContainers> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Call<LxdResponse<String[]>> list() {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).list();
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> create(ContainersPost container) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).create(container);
        }

        @Override
        public Call<LxdResponse<Container>> get(String name) {
            Container metadata = new Container();
            metadata.setArchitecture("architecture");
            // TODO: fill more attributes

            LxdResponse<Container> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);
            return delegate.returningResponse(response).get(name);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> replaceConfiguration(String name, ContainerPut configuration) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).replaceConfiguration(name, configuration);
        }

        @Override
        public Call<LxdResponse<Empty>> updateConfiguration(String name, ContainerPut configuration) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).updateConfiguration(name, configuration);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> rename(String name, ContainerPost configuration) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).rename(name, configuration);
        }

        @Override
        public Call<LxdResponse<ContainerPostMigrationResponse>> migrate(String name, ContainerPost configuration) {
            ContainerPostMigrationResponse metadata = new ContainerPostMigrationResponse();
            metadata.setControl("control");
            metadata.setCriu("criu");
            metadata.setFs("fs");

            LxdResponse<ContainerPostMigrationResponse> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);
            return delegate.returningResponse(response).migrate(name, configuration);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> delete(String name) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).delete(name);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> restoreSnapshot(String name, ContainerSnapshotRestore restore) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).restoreSnapshot(name, restore);
        }

        @Override
        public Call<LxdResponse<String[]>> getConsole(String name) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).getConsole(name);
        }

        @Override
        public Call<LxdResponse<Empty>> clearConsole(String name) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).clearConsole(name);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> execCommand(String container, ContainerExecPost command) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).execCommand(container, command);
        }

        @Override
        public CompletableFuture<Response<ResponseBody>> getFile(String container, String pathToContainerFile) {
            return null;
        }

        @Override
        public CompletableFuture<LxdResponse<Empty>> putFile(String container, String pathToContainerFile,
                RequestBody filePart, int uid, int gid, String mode, String type, String write) {
            return null;
        }

        @Override
        public CompletableFuture<LxdResponse<Empty>> mkdir(String container, String pathToContainerDir, int uid,
                int gid, String mode, String type, String write) {

            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).mkdir(container, pathToContainerDir, uid, gid, mode, type, write);
        }

        @Override
        public CompletableFuture<LxdResponse<Empty>> mklink(String container, String dest, RequestBody source, int uid,
                int gid, String mode, String type, String write) {
            return null;
        }

        @Override
        public CompletableFuture<LxdResponse<Empty>> createLink(String container, String linkFrom, String linkTo,
                int uid, int gid, String mode, String type, String write) {

            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).createLink(container, linkFrom, linkTo, uid, gid, mode, type, write);
        }

        @Override
        public Call<LxdResponse<Empty>> deleteFile(String container, String pathToContainerFile) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).deleteFile(container, pathToContainerFile);
        }

        @Override
        public Call<LxdResponse<String[]>> listSnapshots(String name) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).listSnapshots(name);
        }

        @Override
        public Call<LxdResponse<String[]>> createSnapshot(String name, ContainerSnapshotsPost snapshot) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).createSnapshot(name, snapshot);
        }

        @Override
        public Call<LxdResponse<ContainerSnapshotName>> getSnapshot(String name, String snapshotName) {
            ContainerSnapshotName metadata = new ContainerSnapshotName();
            metadata.setArchitecture("architecture");
            // TODO: fill more attributes

            LxdResponse<ContainerSnapshotName> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);
            return delegate.returningResponse(response).getSnapshot(name, snapshotName);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> renameSnapshot(String name, String snapshotName,
                ContainerSnapshotPost newName) {

            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).renameSnapshot(name, snapshotName, newName);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> migrateSnapshot(String name, String snapshotName,
                ContainerSnapshotPost newName) {

            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).migrateSnapshot(name, snapshotName, newName);
        }

        @Override
        public Call<LxdResponse<Empty>> deleteSnapshot(String name, String snapshotName) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).deleteSnapshot(name, snapshotName);
        }

        @Override
        public Call<LxdResponse<ContainerState>> getState(String name) {
            ContainerState metadata = new ContainerState();
            metadata.setPid(1);
            // TODO: fill more attributes

            LxdResponse<ContainerState> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);
            return delegate.returningResponse(response).getState(name);
        }

        @Override
        public Call<LxdResponse<BackgroundOperation>> changeState(String name, ContainerStatePut newState) {
            LxdResponse<BackgroundOperation> response = TestData.createResponse("sync", 200, "OK", "operation", TestData.createBackgroundOperation());
            return delegate.returningResponse(response).changeState(name, newState);
        }

        @Override
        public Call<LxdResponse<String[]>> listLogFiles(String name) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).listLogFiles(name);
        }

        @Override
        public Call<LxdResponse<String[]>> getLogFile(String name, String logFile) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).getLogFile(name, logFile);
        }

        @Override
        public Call<LxdResponse<Empty>> deleteLogFile(String name, String logFile) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).deleteLogFile(name, logFile);
        }

        @Override
        public Call<LxdResponse<ContainerMetadata>> getMetadata(String name) {
            ContainerMetadata metadata = new ContainerMetadata();
            metadata.setArchitecture("architecture");
            metadata.setCreationDate("today");
            metadata.setExpiryDate("today");
            // TODO: fill more attributes

            LxdResponse<ContainerMetadata> response = TestData.createResponse("sync", 200, "OK", "operation", metadata);
            return delegate.returningResponse(response).getMetadata(name);
        }

        @Override
        public Call<LxdResponse<Empty>> replaceMetadata(String name, ContainerMetadata newMetadata) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).replaceMetadata(name, newMetadata);
        }

        @Override
        public Call<LxdResponse<String[]>> listMetadataTemplates(String name) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"container 1", "container 2"});
            return delegate.returningResponse(response).listMetadataTemplates(name);
        }

        @Override
        public Call<LxdResponse<String[]>> getMetadataTemplate(String name, String template) {
            LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).getMetadataTemplate(name, template);
        }

        @Override
        public CompletableFuture<LxdResponse<Empty>> addMetadataTemplate(String container, String template,
                RequestBody filePart) {
            return null;
        }

        @Override
        public CompletableFuture<LxdResponse<Empty>> replaceMetadataTemplate(String name, String template,
                RequestBody filePart) {
            return null;
        }

        @Override
        public Call<LxdResponse<Empty>> deleteMetadataTemplate(String name, String template) {
            LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
            return delegate.returningResponse(response).deleteMetadataTemplate(name, template);
        }
    }

    public MockContainers(String url) throws Exception {
        super(url);

        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setErrorPercent(0);
        behavior.setDelay(0, TimeUnit.SECONDS);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(getRetrofit(url)).networkBehavior(behavior).build();

        BehaviorDelegate<LxdContainers> delegate = mockRetrofit.create(LxdContainers.class);
        MockLxdContainers mockLxdContainers = new MockLxdContainers(delegate);

        lxdContainers = mockLxdContainers;
    }
}
