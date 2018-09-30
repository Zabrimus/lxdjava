package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.Profile;
import de.serversenke.lxd.client.core.model.ProfilePost;
import de.serversenke.lxd.client.core.model.ProfilePostRename;
import de.serversenke.lxd.client.core.model.ProfilePut;

public class ProfileModelTest extends TestBase {

    @Test
    void testProfilePostDeSerialize() {
        testDeSerialize("request_profile_post.json", ProfilePost.class);
    }

    @Test
    void testProfileNameGetDeSerialize() {
        testDeSerialize("response_profile_name_get.json", Profile.class);
    }

    @Test
    void testProfileNamePutDeSerialize() {
        testDeSerialize("request_profile_name_put.json", ProfilePut.class);
    }

    @Test
    void testProfileNamePatchDeSerialize() {
        testDeSerialize("request_profile_name_patch.json", ProfilePut.class);
    }

    @Test
    void testProfileNameRenameDeSerialize() {
        testDeSerialize("request_profile_name_rename.json", ProfilePostRename.class);
    }
}
