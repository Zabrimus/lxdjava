package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.StoragePool;
import de.serversenke.lxd.client.core.model.StoragePoolPost;
import de.serversenke.lxd.client.core.model.StoragePoolPut;
import de.serversenke.lxd.client.core.model.StoragePoolResources;
import de.serversenke.lxd.client.core.model.StoragePoolVolume;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeConfig;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeMigration;
import de.serversenke.lxd.client.core.model.StoragePoolVolumeRename;
import de.serversenke.lxd.client.core.model.StoragePoolVolumesPost;
import de.serversenke.lxd.client.core.rest.LxdStoragePools;
import lombok.SneakyThrows;

public class StoragePools extends ApiBase {

    private LxdStoragePools lxdStoragePools;

    @SneakyThrows
    public StoragePools(String url) {
        lxdStoragePools = getRetrofit(url).create(LxdStoragePools.class);
    }

    /**
     * GET /1.0/storage-pools
     */
    public ClientResponse<String[]> list() {
        return execute(lxdStoragePools.list());
    }

    /**
     * POST /1.0/storage-pools
     */
    public ClientResponse<Empty> create(StoragePoolPost newPool) {
        return execute(lxdStoragePools.create(newPool));
    }

    /**
     * GET /1.0/storage-pools/{name}
     */
    public ClientResponse<StoragePool> get(String name) {
        return execute(lxdStoragePools.get(name));
    }

    /**
     * PUT /1.0/storage-pools/{name}
     */
    public ClientResponse<Empty> replace(String name, StoragePoolPut replacement) {
        return execute(lxdStoragePools.replace(name, replacement));
    }

    /**
     * PATCH /1.0/storage-pools/{name}
     */
    public ClientResponse<Empty> update(String name, StoragePoolPut replacement) {
        return execute(lxdStoragePools.update(name, replacement));
    }

    /**
     * DELETE /1.0/storage-pools/{name}
     */
    public ClientResponse<Empty> delete(String name) {
        return execute(lxdStoragePools.delete(name));
    }

    /**
     * GET /1.0/storage-pools/{name}/resources
     */
    public ClientResponse<StoragePoolResources> getResources(String name) {
        return execute(lxdStoragePools.getResources(name));
    }

    /**
     * GET /1.0/storage-pools/{name}/volumes
     */
    public ClientResponse<String[]> getVolumes(String name) {
        return execute(lxdStoragePools.getVolumes(name));
    }

    /**
     * POST /1.0/storage-pools/{name}/volumes
     */
    public ClientResponse<Empty> createVolume(String name, StoragePoolVolumesPost volume) {
        return execute(lxdStoragePools.createVolume(name, volume));
    }

    /**
     * POST /1.0/storage-pools/{name}/volumes
     */
    public ClientResponse<Empty> copyVolume(String name, StoragePoolVolumesPost volume) {
        return execute(lxdStoragePools.copyVolume(name, volume));
    }

    /**
     * POST /1.0/storage-pools/{name}/volumes
     */
    public ClientResponse<Empty> migrateVolume(String name, StoragePoolVolumesPost volume) {
        return execute(lxdStoragePools.migrateVolume(name, volume));
    }

    /**
     * POST /1.0/storage-pools/{name}/volumes/{type}/{volume}
     */
    public ClientResponse<StoragePoolVolumeMigration> renameVolume(String name, String type, String volume,
            StoragePoolVolumeRename volumeRename) {
        return execute(lxdStoragePools.renameVolume(name, type, volume, volumeRename));
    }

    /**
     * GET /1.0/storage-pools/{name}/volumes/{type}/{volume}
     */
    public ClientResponse<StoragePoolVolume> getVolume(String name, String type, String volume) {
        return execute(lxdStoragePools.getVolume(name, type, volume));
    }

    /**
     *
     */
    public ClientResponse<Empty> replaceVolumeConfig(String name, String type, String volume,
            StoragePoolVolumeConfig config) {
        return execute(lxdStoragePools.replaceVolumeConfig(name, type, volume, config));
    }

    /**
     * POST /1.0/storage-pools/{name}/volumes/{type}/{volume}
     */
    public ClientResponse<Empty> updateVolumeConfig(String name, String type, String volume,
            StoragePoolVolumeConfig config) {
        return execute(lxdStoragePools.updateVolumeConfig(name, type, volume, config));
    }

    /**
     * POST /1.0/storage-pools/{name}/volumes/{type}/{volume}
     */
    public ClientResponse<Empty> deleteVolume(String name, String type, String volume) {
        return execute(lxdStoragePools.deleteVolume(name, type, volume));
    }
}
