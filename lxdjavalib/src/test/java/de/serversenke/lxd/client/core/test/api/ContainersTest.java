package de.serversenke.lxd.client.core.test.api;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import de.serversenke.lxd.client.core.ClientResponse;
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
import de.serversenke.lxd.client.core.test.mock.MockContainers;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContainersTest extends TestBase {

    private MockContainers containers;

    @BeforeAll
    void beforeAll() {
        try {
            containers = new MockContainers("http://localhost");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void list() {
        ClientResponse<String[]> response = containers.list();

        testCommonResponse(response);
    }

    @Test
    public void create() {
        ContainersPost metadata = new ContainersPost();
        metadata.setArchitecture("architecture");
        metadata.setDescription("description");
        metadata.setName("name");
        // TODO: more attributes to set

        ClientResponse<BackgroundOperation> response = containers.create(metadata);

        testCommonResponse(response);
    }

    @Test
    public void get() {
        ClientResponse<Container> response = containers.get("name");

        testCommonResponse(response);
    }

    @Test
    public void replaceConfiguration() {
        ContainerPut put = new ContainerPut();
        put.setArchitecture("architecture");
        put.setStateful(true);
        // TODO: more attributes to set

        ClientResponse<BackgroundOperation> response = containers.replaceConfiguration("name", put);

        testCommonResponse(response);
    }

    @Test
    public void updateConfiguration() {
        ContainerPut put = new ContainerPut();
        put.setArchitecture("architecture");
        put.setStateful(true);
        // TODO: more attributes to set

        ClientResponse<Empty> response = containers.updateConfiguration("name", put);

        testCommonResponse(response);
    }

    @Test
    public void rename() {
        ContainerPost metadata = new ContainerPost();
        metadata.setContainerOnly(true);
        metadata.setLive(true);
        // TODO: more attributes to set

        ClientResponse<BackgroundOperation> response = containers.rename("name", metadata);

        testCommonResponse(response);
    }

    @Test
    public void migrate() {
        ContainerPost metadata = new ContainerPost();
        metadata.setContainerOnly(true);
        metadata.setLive(true);
        // TODO: more attributes to set

        ClientResponse<ContainerPostMigrationResponse> response = containers.migrate("name", metadata);

        testCommonResponse(response);
    }

    @Test
    public void delete() {
        ClientResponse<BackgroundOperation> response = containers.delete("name");

        testCommonResponse(response);
    }

    @Test
    public void restoreSnaphot() {
        ContainerSnapshotRestore metadata = new ContainerSnapshotRestore();
        metadata.setRestore("restore");

        ClientResponse<BackgroundOperation> response = containers.restoreSnapshot("name", metadata);

        testCommonResponse(response);
    }

    @Test
    public void getConsole() {
        ClientResponse<String[]> response = containers.getConsole("name");

        testCommonResponse(response);
    }

    @Test
    public void clearConsole() {
        ClientResponse<Empty> response = containers.clearConsole("name");

        testCommonResponse(response);
    }

    @Test
    public void execCommand() {
        ContainerExecPost metadata = new ContainerExecPost();
        metadata.setCommand(new String[] {"ls", "-la"});
        metadata.setHeight(100);
        // TODO: more attributes to set

        ClientResponse<BackgroundOperation> response = containers.execCommand("container", metadata);

        testCommonResponse(response);
    }

    @Test
    public void getFileContent() {
//		LxdFile response = containers.getFileContent("container", "path");
//
//		assertEquals(response.getGid(), 100);
//		assertEquals(response.getLength(), 200);
//		assertEquals(response.getUid(), 300);
    }

    @Test
    public void mkdir() {
//		LxdFile metadata = LxdFile.builder().gid(100).uid(200).filename("path").build();
//
//		ClientResponse<Empty> response = containers.mkdir("container", "path", metadata);
//		testCommonResponse(response);
    }

    @Test
    public void mklink() {
//		LxdFile metadata = LxdFile.builder().gid(100).uid(200).filename("path").build();
//
//		ClientResponse<Empty> response = containers.mklink("container", "path", metadata);
//		testCommonResponse(response);
    }

    @Test
    public void deleteFile() {
        ClientResponse<Empty> response = containers.deleteFile("container", "path");

        testCommonResponse(response);
    }

    @Test
    public void listSnapshots() {
        ClientResponse<String[]> response = containers.listSnapshots("container");

        testCommonResponse(response);
    }

    @Test
    public void createSnapshot() {
        ContainerSnapshotsPost metadata = new ContainerSnapshotsPost();
        metadata.setName("name");
        metadata.setStateful(true);

        ClientResponse<String[]> response = containers.createSnapshot("container", metadata);

        testCommonResponse(response);
    }

    @Test
    public void getSnapshot() {
        ClientResponse<ContainerSnapshotName> response = containers.getSnapshot("container", "name");

        testCommonResponse(response);
    }

    @Test
    public void renameSnapshot() {
        ContainerSnapshotPost metadata = new ContainerSnapshotPost();
        metadata.setName("name");
        metadata.setLive(true);

        ClientResponse<BackgroundOperation> response = containers.renameSnapshot("container", "name", metadata);

        testCommonResponse(response);
    }

    @Test
    public void migrateSnapshot() {
        ContainerSnapshotPost metadata = new ContainerSnapshotPost();
        metadata.setName("name");
        metadata.setLive(true);

        ClientResponse<BackgroundOperation> response = containers.migrateSnapshot("container", "name", metadata);

        testCommonResponse(response);
    }

    @Test
    public void deleteSnapshot() {
        ClientResponse<Empty> response = containers.deleteSnapshot("container", "name");

        testCommonResponse(response);
    }

    @Test
    public void getState() {
        ClientResponse<ContainerState> response = containers.getState("container");

        testCommonResponse(response);
    }

    @Test
    public void changeState() {
        ContainerStatePut metadata = new ContainerStatePut();
        metadata.setAction("action");
        metadata.setForce(true);
        metadata.setStateful(true);
        metadata.setTimeout(100);

        ClientResponse<BackgroundOperation> response = containers.changeState("container", metadata);

        testCommonResponse(response);
    }

    @Test
    public void listLogFiles() {
        ClientResponse<String[]> response = containers.listLogFiles("name");

        testCommonResponse(response);
    }

    @Test
    public void getLogFile() {
        ClientResponse<String[]> response = containers.getLogFile("name", "logFile");

        testCommonResponse(response);
    }

    @Test
    public void deleteLogFile() {
        ClientResponse<Empty> response = containers.deleteLogFile("name", "logFile");

        testCommonResponse(response);
    }

    @Test
    public void getMetadata() {
        ClientResponse<ContainerMetadata> response = containers.getMetadata("name");

        testCommonResponse(response);
    }

    @Test
    public void replaceMetadata() {
        ContainerMetadata metadata = new ContainerMetadata();
        metadata.setArchitecture("architecture");
        metadata.setCreationDate("today");
        metadata.setExpiryDate("today");
        // TODO: more attributes to set

        ClientResponse<Empty> response = containers.replaceMetadata("name", metadata);

        testCommonResponse(response);
    }

    @Test
    public void listMetadataTemplates() {
        ClientResponse<String[]> response = containers.listMetadataTemplates("name");

        testCommonResponse(response);
    }

    @Test
    public void getMetadataTemplate() {
        ClientResponse<String[]> response = containers.getMetadataTemplate("name", "template");

        testCommonResponse(response);
    }

    @Test
    public void deleteMetadataTemplate() {
        ClientResponse<Empty> response = containers.deleteMetadataTemplate("name", "template");

        testCommonResponse(response);
    }

}
