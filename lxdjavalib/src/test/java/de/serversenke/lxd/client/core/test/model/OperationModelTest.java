package de.serversenke.lxd.client.core.test.model;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.Operation;

public class OperationModelTest extends TestBase {

    @Test
    void testOperationUuidGetDeSerialize() {
        testDeSerialize("response_operation_uuid_get.json", Operation.class);
    }
}
