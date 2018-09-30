package de.serversenke.lxd.client.core.test.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.model.StatusCode;

public class TestData {
	
	public static String read(String filename) throws IOException, URISyntaxException {
		String content = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filename).toURI())));
		return content;
	}
	
	public static <T> LxdResponse<T> createResponse(String type, int code, String status, String operation, T metadata) {
		LxdResponse<T> response = new LxdResponse<>();
		response.setResponseType(type);
		response.setStatusCode(code);
		response.setStatus(status);
		response.setOperation(operation);
		response.setMetadata(metadata);

		return response;
	}
	
	public static BackgroundOperation createBackgroundOperation() {
		BackgroundOperation metadata = new BackgroundOperation();
		metadata.setId("id");
		metadata.setClazz("class");
		metadata.setCreatedAt(Instant.now());
		metadata.setUpdatedAt(Instant.now());
		metadata.setStatus("status");
		metadata.setStatusCode(StatusCode.Running);
		metadata.setMayCancel(true);
		metadata.setErrorMessage("error message");
		
		Map<String, List<String>> resources = new HashMap<>();
		resources.put("containers", Arrays.asList("containers"));			
		metadata.setResources(resources);
		
		Map<String, Map<String, String>> meta = new HashMap<>();
		metadata.setMetadata(meta);

		return metadata;
	}
}
