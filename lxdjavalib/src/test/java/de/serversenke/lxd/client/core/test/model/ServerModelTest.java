package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.ServerConfiguration;

public class ServerModelTest extends TestBase {

    @Test
    void testServerDeSerialize() {
        testDeSerialize("response_server_configuration.json", ServerConfiguration.class);
    }

    @Test
    void testServerApiVersion() {
        testDeSerialize("response_server_api.json", String[].class);
    }
}
