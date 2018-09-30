package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.ImageAliasGet;
import de.serversenke.lxd.client.core.model.ImageAliasPatch;
import de.serversenke.lxd.client.core.model.ImageAliasPost;
import de.serversenke.lxd.client.core.model.ImageAliasPut;
import de.serversenke.lxd.client.core.model.ImageGet;
import de.serversenke.lxd.client.core.model.ImagePut;
import de.serversenke.lxd.client.core.model.ImagesPost;

public class ImageModelTest extends TestBase {

    @Test
    void testImagePost1DeSerialize() {
        testDeSerialize("request_image_post1.json", ImagesPost.class);
    }

    @Test
    void testImagePost2DeSerialize() {
        testDeSerialize("request_image_post2.json", ImagesPost.class);
    }

    @Test
    void testImagePost3DeSerialize() {
        testDeSerialize("request_image_post3.json", ImagesPost.class);
    }

    @Test
    void testImageGetDeSerialize() {
        testDeSerialize("response_image_get.json", ImageGet.class);
    }

    @Test
    void testImagePutDeSerialize() {
        testDeSerialize("request_image_put.json", ImagePut.class);
    }

    @Test
    void testImagePatchDeSerialize() {
        testDeSerialize("request_image_patch.json", ImagePut.class);
    }

    @Test
    void testImageAliasPostDeSerialize() {
        testDeSerialize("request_image_alias_post.json", ImageAliasPost.class);
    }

    @Test
    void testImageAliasNameGetDeSerialize() {
        testDeSerialize("request_image_alias_name_get.json", ImageAliasGet.class);
    }

    @Test
    void testImageAliasNamePutDeSerialize() {
        testDeSerialize("request_image_alias_name_put.json", ImageAliasPut.class);
    }

    @Test
    void testImageAliasNamePatchDeSerialize() {
        testDeSerialize("request_image_alias_name_patch.json", ImageAliasPatch.class);
    }

    @Test
    void testImageAliasNamePostDeSerialize() {
        testDeSerialize("request_image_alias_name_patch.json", ImageAliasPost.class);
    }

}
