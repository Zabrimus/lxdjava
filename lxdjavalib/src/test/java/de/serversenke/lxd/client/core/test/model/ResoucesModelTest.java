package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.SystemResources;

public class ResoucesModelTest extends TestBase {

    @Test
    void testProfilePostDeSerialize() {
        testDeSerialize("request_resources_get.json", SystemResources.class);
    }
}
