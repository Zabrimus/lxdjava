package de.serversenke.lxd.client.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.ImageAliasGet;
import de.serversenke.lxd.client.core.model.ImageAliasPatch;
import de.serversenke.lxd.client.core.model.ImageAliasPost;
import de.serversenke.lxd.client.core.model.ImageAliasPostRename;
import de.serversenke.lxd.client.core.model.ImageAliasPut;
import de.serversenke.lxd.client.core.model.ImageGet;
import de.serversenke.lxd.client.core.model.ImagePut;
import de.serversenke.lxd.client.core.model.ImagesPost;
import de.serversenke.lxd.client.core.model.LxdFile;
import de.serversenke.lxd.client.core.model.LxdFile.FileType;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.rest.LxdImages;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Path;


public class Images extends ApiBase {

    private LxdImages lxdImages;

    public Images(String url) {
        lxdImages = getRetrofit(url).create(LxdImages.class);
    }

    /**
     * GET /1.0/images
     */
    public ClientResponse<String[]> list() {
        return execute(lxdImages.list());
    }

    /**
     * POST /1.0/images
     */
    public ClientResponse<Empty> uploadImage(String localImageFilename) throws FileNotFoundException {
        File file = new File(localImageFilename);

        if (!file.exists()) {
            throw new FileNotFoundException(localImageFilename);
        }

        RequestBody uploadFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        CompletableFuture<LxdResponse<Empty>> putter = lxdImages.uploadImage(uploadFile);
        try {
            LxdResponse<Empty> res = putter.get();
            return new ClientResponse<Empty>(null, res.getStatusCode(), res.getMetadata(), res.getResponseType(), res.getStatus(), res.getOperation(), res.getErrorCode(), res.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST /1.0/images
     */
    public ClientResponse<BackgroundOperation> createImage(@Body ImagesPost imagePost) {
        return execute(lxdImages.createImage(imagePost));
    }

    /**
     * GET /1.0/images/{fingerprint}
     */
    public ClientResponse<ImageGet> get(String fingerprint, String secret) {
        return execute(lxdImages.get(fingerprint, secret));
    }

    /**
     * PUT /1.0/images/{fingerprint}
     */
    public ClientResponse<Empty> replaceProperties(String fingerprint, @Body ImagePut properties) {
        return execute(lxdImages.replaceProperties(fingerprint, properties));
    }

    /**
     * PATCH /1.0/images/{fingerprint}
     */
    public ClientResponse<Empty> updateProperties(String fingerprint, @Body ImagePut properties) {
        return execute(lxdImages.updateProperties(fingerprint, properties));
    }

    /**
     * DELETE /1.0/images/{fingerprint}
     */
    public ClientResponse<Empty> delete(String fingerprint, String secret) {
        return execute(lxdImages.delete(fingerprint, secret));
    }

    /**
     * GET /1.0/images/{fingerprint}/export
     */
    public LxdFile download(String fingerprint, String secret, String pathToSavedFile) {
        CompletableFuture<Response<ResponseBody>> download = lxdImages.download(fingerprint, secret);

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
     * POST /1.0/images/{fingerprint}/refresh
     */
    public ClientResponse<BackgroundOperation> refresh(String fingerprint) {
        return execute(lxdImages.refresh(fingerprint));
    }

    /**
     * POST /1.0/images/<fingerprint>/secret
     */
    public ClientResponse<Map<String, String>> createSecret(String fingerprint) {
        return execute(lxdImages.createSecretKey(fingerprint));
    }

    /**
     * GET /1.0/images/aliases
     */
    public ClientResponse<String[]> aliases() {
        return execute(lxdImages.aliases());
    }

    /**
     * POST /1.0/images/aliases
     */
    public ClientResponse<String[]> createAlias(ImageAliasPost newAlias) {
        return execute(lxdImages.createAlias(newAlias));
    }

    /**
     * GET /1.0/images/aliases/{name}
     */
    public ClientResponse<ImageAliasGet> getAlias(String name) {
        return execute(lxdImages.getAlias(name));
    }

    /**
     * PUT /1.0/images/aliases/{name}
     */
    public ClientResponse<Empty> replaceAlias(String name, ImageAliasPut replacement) {
        return execute(lxdImages.replaceAlias(name, replacement));
    }

    /**
     * PATCH /1.0/images/aliases/{name}
     */
    public ClientResponse<Empty> changeAlias(String name, ImageAliasPatch replacement) {
        return execute(lxdImages.changeAlias(name, replacement));
    }

    /**
     * POST /1.0/images/aliases/{name}
     */
    public ClientResponse<Empty> renameAlias(String name, ImageAliasPostRename replacement) {
        return execute(lxdImages.renameAlias(name, replacement));
    }

    /**
     * DELETE /1.0/images/aliases/{name}
     */
    public ClientResponse<Empty> deleteAlias(@Path("name") String name) {
        return execute(lxdImages.deleteAlias(name));
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
