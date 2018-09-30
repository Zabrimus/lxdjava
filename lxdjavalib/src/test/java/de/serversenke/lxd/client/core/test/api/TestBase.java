package de.serversenke.lxd.client.core.test.api;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.model.StatusCode;

public class TestBase {

	protected <T> void testCommonResponse(ClientResponse<T> resp) {
		if (resp.getType() != ClientResponse.ResponseType.ERROR) {		
			assertEquals(resp.getType(), ClientResponse.ResponseType.SYNC);
			assertEquals(resp.getStatusCode(), StatusCode.Success);
			assertEquals(resp.getOperation(), "operation");
			assertEquals(resp.getErrorCode(), 0);
			assertNull(resp.getErrorMessage());
			assertEquals(resp.getHeaders().size(), 0);
		}
	}

}
