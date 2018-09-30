package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.StoragePool;
import de.serversenke.lxd.client.core.model.StoragePoolPost;
import de.serversenke.lxd.client.core.model.StoragePoolPut;
import de.serversenke.lxd.client.core.model.StoragePoolResources;
import de.serversenke.lxd.client.core.model.StoragePoolVolume;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeConfig;
import de.serversenke.lxd.client.core.model.StoragePoolVolumePost;
import de.serversenke.lxd.client.core.model.StoragePoolVolumesPost;

public class StoragePoolModelTest extends TestBase {

    @Test
    void testStoragePoolPostDeSerialize() {
        testDeSerialize("request_storagepool_post.json", StoragePoolPost.class);
    }

    @Test
    void testStoragePoolNameGetDeSerialize() {
        testDeSerialize("request_storagepool_name_get.json", StoragePool.class);
    }

    @Test
    void testStoragePoolNamePutDeSerialize() {
        testDeSerialize("request_storagepool_name_put.json", StoragePoolPut.class);
    }

    @Test
    void testStoragePoolNamePatchDeSerialize() {
        testDeSerialize("request_storagepool_name_patch.json", StoragePoolPut.class);
    }

    @Test
    void testStoragePoolNameResourcesDeSerialize() {
        testDeSerialize("request_storagepool_name_resources.json", StoragePoolResources.class);
    }

    @Test
    void testStoragePoolNameVolumesPostDeSerialize() {
        testDeSerialize("request_storagepool_name_volumes_post.json", StoragePoolVolumesPost.class);
    }

    @Test
    void testStoragePoolNameVolumesCopyDeSerialize() {
        testDeSerialize("request_storagepool_name_volumes_copy.json", StoragePoolVolumesPost.class);
    }

    @Test
    void testStoragePoolNameVolumesMigrationDeSerialize() {
        testDeSerialize("request_storagepool_name_volumes_migration.json", StoragePoolVolumesPost.class);
    }

    @Test
    void testStoragePoolNameVolumesRenameDeSerialize() {
        testDeSerialize("request_storagepool_name_volume_rename.json", StoragePoolVolumePost.class);
    }

    @Test
    void testStoragePoolNameVolumesMigrateDeSerialize() {
        testDeSerialize("request_storagepool_name_volume_migrate.json", StoragePoolVolumePost.class);
    }

    @Test
    void testStoragePoolNameVolumeGetDeSerialize() {
        testDeSerialize("request_storagepool_name_volume_config_get.json", StoragePoolVolume.class);
    }

    @Test
    void testStoragePoolNameVolumeReplaceConfigGetDeSerialize() {
        testDeSerialize("request_storagepool_name_volume_replace_config.json", StoragePoolVolumeConfig.class);
    }

    @Test
    void testStoragePoolNameVolumePatchConfigGetDeSerialize() {
        testDeSerialize("request_storagepool_name_volume_patch_config.json", StoragePoolVolumeConfig.class);
    }

}
