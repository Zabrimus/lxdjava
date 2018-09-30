package de.serversenke.lxd.client.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

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
import de.serversenke.lxd.client.core.model.LxdFile;
import de.serversenke.lxd.client.core.model.LxdFile.FileType;
import de.serversenke.lxd.client.core.model.LxdFile.WriteType;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.rest.LxdContainers;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class Containers extends ApiBase {

    protected LxdContainers lxdContainers;

    @SneakyThrows
    public Containers(String url) {
        lxdContainers = getRetrofit(url).create(LxdContainers.class);
    }

    /**
     * GET /1.0/containers
     */
    public ClientResponse<String[]> list() {
        return execute(lxdContainers.list());
    }

    /**
     * POST /1.0/containers
     */
    public ClientResponse<BackgroundOperation> create(ContainersPost container) {
        return execute(lxdContainers.create(container));
    }

    /**
     * GET /1.0/containers/{name}
     */
    public ClientResponse<Container> get(String name) {
        return execute(lxdContainers.get(name));
    }

    /**
     * PUT /1.0/containers/{name}
     */
    public ClientResponse<BackgroundOperation> replaceConfiguration(String name, ContainerPut configuration) {
        return execute(lxdContainers.replaceConfiguration(name, configuration));
    }

    /**
     * PATCH /1.0/containers/{name}
     */
    public ClientResponse<Empty> updateConfiguration(String name, ContainerPut configuration) {
        return execute(lxdContainers.updateConfiguration(name, configuration));
    }

    /**
     * POST /1.0/containers/{name}
     */
    public ClientResponse<BackgroundOperation> rename(String name, ContainerPost configuration) {
        return execute(lxdContainers.rename(name, configuration));
    }

    /**
     * POST /1.0/containers/{name}
     */
    public ClientResponse<ContainerPostMigrationResponse> migrate(String name, ContainerPost configuration) {
        return execute(lxdContainers.migrate(name, configuration));
    }

    /**
     * DELETE /1.0/containers/{name}
     */
    public ClientResponse<BackgroundOperation> delete(String name) {
        return execute(lxdContainers.delete(name));
    }

    /**
     * PUT /1.0/containers/{name}
     */
    public ClientResponse<BackgroundOperation> restoreSnapshot(String name, ContainerSnapshotRestore restore) {
        return execute(lxdContainers.restoreSnapshot(name, restore));
    }

    /**
     * GET /1.0/containers/{name}/console
     */
    public ClientResponse<String[]> getConsole(String name) {
        return execute(lxdContainers.getConsole(name));
    }

    /**
     * DELETE /1.0/containers/{name}/console
     */
    public ClientResponse<Empty> clearConsole(String name) {
        return execute(lxdContainers.clearConsole(name));
    }

    /**
     * POST /1.0/containers/{name}/exec"
     */
    public ClientResponse<BackgroundOperation> execCommand(String container, ContainerExecPost command) {
        return execute(lxdContainers.execCommand(container, command));
    }

    // TODO: Directories?
    /**
     * GET /1.0/containers/{name}/files
     */
    public LxdFile getFileContent(String container, String pathToContainerFile) {
        CompletableFuture<Response<ResponseBody>> download = lxdContainers.getFile(container, pathToContainerFile);

        try {
            Response<ResponseBody> response = download.get();

            LxdFile result = fillFileProperties(response);

            try (ResponseBody body = response.body()) {
                result.setContent(body.bytes());
                result.setLength(body.contentLength());
                result.setContentType(body.contentType());

                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GET /1.0/containers/{name}/files
     */
    public LxdFile downloadFile(String container, String pathToContainerFile, String pathToSavedFile) {
        CompletableFuture<Response<ResponseBody>> download = lxdContainers.getFile(container, pathToContainerFile);

        try {
            Response<ResponseBody> response = download.get();

            LxdFile result = fillFileProperties(response);

            try (ResponseBody body = response.body()) {
                result.setLength(body.contentLength());
                result.setContentType(body.contentType());
                result.setFilename(pathToSavedFile);

                Files.copy(body.byteStream(), FileSystems.getDefault().getPath(pathToSavedFile));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST /1.0/containers/{name}/files
     */
    public ClientResponse<Empty> uploadFile(String container, String pathToContainerFile, LxdFile localFile) throws FileNotFoundException {
        File file = new File(localFile.getFilename());

        if (!file.exists()) {
            throw new FileNotFoundException(localFile.getFilename());
        }

        RequestBody uploadFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        CompletableFuture<LxdResponse<Empty>> putter = lxdContainers.putFile(container, pathToContainerFile, uploadFile, localFile.getUid(), localFile.getGid(), localFile.getMode(), FileType.FILE.getFileType(), localFile.getWriteType().getWriteType());
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST /1.0/containers/{name}/files
     */
    public ClientResponse<Empty> createFile(String container, String pathToContainerFile, LxdFile localFile, String content) throws FileNotFoundException {
        RequestBody uploadFile = RequestBody.create(MediaType.parse("application/octet-stream"), content);
        CompletableFuture<LxdResponse<Empty>> putter = lxdContainers.putFile(container, pathToContainerFile, uploadFile, localFile.getUid(), localFile.getGid(), localFile.getMode(), FileType.FILE.getFileType(), localFile.getWriteType().getWriteType());
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST /1.0/containers/{name}/files
     */
    public ClientResponse<Empty> mkdir(String container, String pathToContainerDir, LxdFile perm) {
        CompletableFuture<LxdResponse<Empty>> putter = lxdContainers.mkdir(container, pathToContainerDir, perm.getUid(), perm.getGid(), perm.getMode(), FileType.DIRECTORY.getFileType(), WriteType.OVERWRITE.getWriteType());
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST /1.0/containers/{name}/files
     */
    public ClientResponse<Empty> mklink(String container, String linkDest, LxdFile linkSource) {
        RequestBody sb = RequestBody.create(MediaType.parse("text/plain"), linkSource.getFilename());

        CompletableFuture<LxdResponse<Empty>> putter = lxdContainers.mklink(container, linkDest, sb, linkSource.getUid(), linkSource.getGid(), linkSource.getMode(), FileType.SYMLINK.getFileType(), WriteType.OVERWRITE.getWriteType());
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DELETE /1.0/containers/{name}/files
     */
    public ClientResponse<Empty> deleteFile(String container, String pathToContainerFile) {
        return execute(lxdContainers.deleteFile(container, pathToContainerFile));
    }

    /**
     * GET /1.0/containers/{name}/snapshots
     */
    public ClientResponse<String[]> listSnapshots(String name) {
        return execute(lxdContainers.listSnapshots(name));
    }

    /**
     * POST /1.0/containers/{name}/snapshots
     */
    public ClientResponse<String[]> createSnapshot(String name, ContainerSnapshotsPost snapshot) {
        return execute(lxdContainers.createSnapshot(name, snapshot));
    }

    /**
     * GET /1.0/containers/{name}/snapshots/{sname}
     */
    public ClientResponse<ContainerSnapshotName> getSnapshot(String name, String snapshotName) {
        return execute(lxdContainers.getSnapshot(name, snapshotName));
    }

    /**
     * POST /1.0/containers/{name}/snapshots/{sname}
     */
    public ClientResponse<BackgroundOperation> renameSnapshot(String name, String snapshotName, ContainerSnapshotPost newName) {
        return execute(lxdContainers.renameSnapshot(name, snapshotName, newName));
    }

    /**
     * POST /1.0/containers/{name}/snapshots/{sname}
     */
    public ClientResponse<BackgroundOperation> migrateSnapshot(String name, String snapshotName, ContainerSnapshotPost newName) {
        return execute(lxdContainers.migrateSnapshot(name, snapshotName, newName));
    }

    /**
     * DELETE /1.0/containers/{name}/snapshots/{sname}
     */
    public ClientResponse<Empty> deleteSnapshot(String name, String snapshotName) {
        return execute(lxdContainers.deleteSnapshot(name, snapshotName));
    }

    /**
     * GET /1.0/containers/{name}/state
     */
    public ClientResponse<ContainerState> getState(String name) {
        return execute(lxdContainers.getState(name));
    }

    /**
     * PUT /1.0/containers/{name}/state
     */
    public ClientResponse<BackgroundOperation> changeState(String name, ContainerStatePut newState) {
        return execute(lxdContainers.changeState(name, newState));
    }

    /**
     * GET /1.0/containers/{name}/logs
     */
    public ClientResponse<String[]> listLogFiles(String name) {
        return execute(lxdContainers.listLogFiles(name));
    }

    /**
     * GET /1.0/containers/{name}/logs/{logfile}
     */
    public ClientResponse<String[]> getLogFile(String name, String logFile) {
        return execute(lxdContainers.getLogFile(name, logFile));
    }

    /**
     * DELETE /1.0/containers/{name}/logs/{logfile}
     */
    public ClientResponse<Empty> deleteLogFile(String name, String logFile) {
        return execute(lxdContainers.deleteLogFile(name, logFile));
    }

    /**
     * GET /1.0/containers/{name}/metadata
     */
    public ClientResponse<ContainerMetadata> getMetadata(String name) {
        return execute(lxdContainers.getMetadata(name));
    }

    /**
     * PUT /1.0/containers/{name}/metadata
     */
    public ClientResponse<Empty> replaceMetadata(String name, ContainerMetadata newMetadata) {
        return execute(lxdContainers.replaceMetadata(name, newMetadata));
    }

    /**
     * GET /1.0/containers/{name}/metadata
     */
    public ClientResponse<String[]> listMetadataTemplates(String name) {
        return execute(lxdContainers.listMetadataTemplates(name));
    }

    /**
     * GET /1.0/containers/{name}/metadata/templates
     */
    public ClientResponse<String[]> getMetadataTemplate(String name, String template) {
        return execute(lxdContainers.getMetadataTemplate(name, template));
    }

    /**
     * POST /1.0/containers/{name}/metadata/templates
     */
    public ClientResponse<Empty> addMetadataTemplate(String container, String pathToContainerFile, LxdFile localFile) throws FileNotFoundException {
        File file = new File(localFile.getFilename());

        if (!file.exists()) {
            throw new FileNotFoundException(localFile.getFilename());
        }

        RequestBody uploadFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        CompletableFuture<LxdResponse<Empty>> putter = lxdContainers.addMetadataTemplate(container, pathToContainerFile, uploadFile);
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * PUT /1.0/containers/{name}/metadata/templates
     */
    public ClientResponse<Empty> replaceMetadataTemplate(String container, String pathToContainerFile, LxdFile localFile) throws FileNotFoundException {
        File file = new File(localFile.getFilename());

        if (!file.exists()) {
            throw new FileNotFoundException(localFile.getFilename());
        }

        RequestBody uploadFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        CompletableFuture<LxdResponse<Empty>> putter = lxdContainers.replaceMetadataTemplate(container, pathToContainerFile, uploadFile);
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DELETE /1.0/containers/{name}/metadata/templates
     */
    public ClientResponse<Empty> deleteMetadataTemplate(String name, String template) {
        return execute(lxdContainers.deleteMetadataTemplate(name, template));
    }

    private LxdFile fillFileProperties(Response<ResponseBody> response) {
        LxdFile result = LxdFile.builder() //
                            .headers(response.headers()) //
                            .gid(Integer.parseInt(response.headers().get("X-LXD-gid"))) //
                            .uid(Integer.parseInt(response.headers().get("X-LXD-uid"))) //
                            .mode(response.headers().get("X-LXD-mode")) //
                            .build();

        String type = response.headers().get("X-LXD-mode");

        if ("DIRECTORY".equalsIgnoreCase(type)) {
            result.setFileType(FileType.DIRECTORY);
        } else if ("FILE".equalsIgnoreCase(type)) {
            result.setFileType(FileType.FILE);
        }
        return result;
    }
}
