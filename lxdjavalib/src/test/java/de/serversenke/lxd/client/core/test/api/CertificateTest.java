package de.serversenke.lxd.client.core.test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.model.Certificate;
import de.serversenke.lxd.client.core.model.CertificatePost;
import de.serversenke.lxd.client.core.model.CertificatePut;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.test.mock.MockCertificates;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificateTest extends TestBase {
	
	private MockCertificates certificate;
	
	@BeforeAll
	void beforeAll() {
		try {
			certificate = new MockCertificates("http://localhost");
		} catch (Exception e) {
			fail(e);
		}
	}
	
    @Test
    void testCertificateList() {
    	ClientResponse<String[]> resp = certificate.list();
    	
    	testCommonResponse(resp);
    	assertEquals(resp.getResponse().length, 2);
    }

    @Test
    void testCertificateGet() {
    	String fp = "my fingerprint";
    	
    	ClientResponse<Certificate> resp = certificate.get(fp);
    	
    	testCommonResponse(resp);
    	assertEquals(resp.getResponse().getFingerprint(), fp);
    }
    
    @Test
	public void replaceProperties() {
    	CertificatePut properties = new CertificatePut();
    	properties.setName("name");
    	properties.setType("type");
    	
		String fingerprint = "my fingerprint";
		ClientResponse<Empty> resp = certificate.replaceProperties(fingerprint, properties);

		testCommonResponse(resp);
	}

	@Test
	public void updateProperties() {
    	CertificatePut properties = new CertificatePut();
    	properties.setName("name");
    	properties.setType("type");
    	
		String fingerprint = "my fingerprint";
		ClientResponse<Empty> resp = certificate.updateProperties(fingerprint, properties);

		testCommonResponse(resp);
	}

	@Test
	public void add() {
		CertificatePost properties = new CertificatePost();
		properties.setCertificate("certificate");
		properties.setName("name");
		properties.setPassword("password");
		properties.setType("type");
		
		ClientResponse<Empty> resp = certificate.add(properties);

		testCommonResponse(resp);
	}

	@Test
	public void deleteProperties() {
		String fingerprint = "my fingerprint";

		ClientResponse<Empty> resp = certificate.delete(fingerprint);
		
		testCommonResponse(resp);
	}
}
