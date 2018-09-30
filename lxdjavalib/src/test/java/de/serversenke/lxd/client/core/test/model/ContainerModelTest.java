package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.Container;
import de.serversenke.lxd.client.core.model.ContainerConsoleControlPost;
import de.serversenke.lxd.client.core.model.ContainerConsolePost;
import de.serversenke.lxd.client.core.model.ContainerExecControlWS;
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
import de.serversenke.lxd.client.core.model.LxdResponse;

public class ContainerModelTest extends TestBase {

    @Test
    void testContainersPost1DeSerialize() {
        testDeSerialize("request_containers_post1.json", ContainersPost.class);
    }

    @Test
    void testContainersPost2DeSerialize() {
        testDeSerialize("request_containers_post2.json", ContainersPost.class);
    }

    @Test
    void testContainersPost3DeSerialize() {
        testDeSerialize("request_containers_post3.json", ContainersPost.class);
    }

    @Test
    void testContainersPost4DeSerialize() {
        testDeSerialize("request_containers_post4.json", ContainersPost.class);
    }

    @Test
    void testContainersPost5DeSerialize() {
        testDeSerialize("request_containers_post5.json", ContainersPost.class);
    }

    @Test
    void testContainersPost6DeSerialize() {
        testDeSerialize("request_containers_post6.json", ContainersPost.class);
    }

    @Test
    void testContainersPost7DeSerialize() {
        testDeSerialize("request_containers_post7.json", ContainersPost.class);
    }

    @Test
    void testContainersPost8DeSerialize() {
        testDeSerialize("request_containers_post8.json", ContainersPost.class);
    }

    @Test
    void testContainersPost9DeSerialize() {
        testDeSerialize("request_containers_post9.json", ContainersPost.class);
    }

    @Test
    void testContainersNamePostDeSerialize() {
        testDeSerialize("response_containers_name_get.json", Container.class);
    }

    @Test
    void testContainersNameReplaceConfigurationDeSerialize() {
        testDeSerialize("request_container_name_replace_configuration.json", ContainerPut.class);
    }

    @Test
    void testContainersSnapshotRestoreDeSerialize() {
        testDeSerialize("request_container_name_snapshot_restore.json", ContainerSnapshotRestore.class);
    }

    @Test
    void testContainersPatchDeSerialize() {
        testDeSerialize("request_container_name_patch.json", ContainerPut.class);
    }

    @Test
    void testContainersPutDeSerialize() {
        testDeSerialize("request_container_name_put.json", ContainerPost.class);
    }

    @Test
    void testContainersPutMigrationDeSerialize() {
        testDeSerialize("request_container_name_put_migration.json", ContainerPost.class);
    }

    @Test
    void testContainersPostResponseDeSerialize() {
        testDeSerialize("response_container_name_post.json", ContainerPostMigrationResponse.class);
    }

    @Test
    void testContainersConsolePostDeSerialize() {
        testDeSerialize("request_container_console_post.json", ContainerConsolePost.class);
    }

    @Test
    void testContainersConsoleControlPostDeSerialize() {
        testDeSerialize("request_container_console_control_post.json", ContainerConsoleControlPost.class);
    }

    @Test
    void testContainersExecControlPostDeSerialize() {
        testDeSerialize("request_container_exec_post.json", ContainerExecPost.class);
    }

    @Test
    void testContainersExecControlControlWS1DeSerialize() {
        testDeSerialize("request_container_exec_control1.json", ContainerExecControlWS.class);
    }

    @Test
    void testContainersExecControlControlWS2DeSerialize() {
        testDeSerialize("request_container_exec_control2.json", ContainerExecControlWS.class);
    }

    @Test
    void testContainersSnapshotPostDeSerialize() {
        testDeSerialize("request_containers_snapshots_post.json", ContainerSnapshotsPost.class);
    }

    @Test
    void testContainersSnapshotNameDeSerialize() {
        testDeSerialize("response_container_snapshot_name.json", ContainerSnapshotName.class);
    }

    @Test
    void testContainersSnapshotRenameDeSerialize() {
        testDeSerialize("request_container_snapshot_rename.json", ContainerSnapshotPost.class);
    }

    @Test
    void testContainersSnapshotMigrateDeSerialize() {
        testDeSerialize("request_container_snapshot_migrate.json", ContainerSnapshotPost.class);
    }

    @Test
    void testContainersStateDeSerialize() {
        testDeSerialize("response_container_state.json", ContainerState.class);
    }

    @Test
    void testContainersStatePutDeSerialize() {
        testDeSerialize("request_container_state_put.json", ContainerStatePut.class);
    }

    @Test
    void testContainerMetadataDeSerialize() {
        testDeSerialize("response_container_metadata.json", ContainerMetadata.class);
    }

    @Test
    void testContainerExecResponseDeSerialize() {
        testDeSerialize("response_container_exec.json", LxdResponse.class);
    }

}

