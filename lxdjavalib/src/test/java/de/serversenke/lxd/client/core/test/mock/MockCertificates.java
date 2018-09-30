package de.serversenke.lxd.client.core.test.mock;

import java.util.concurrent.TimeUnit;

import de.serversenke.lxd.client.core.Certificates;
import de.serversenke.lxd.client.core.model.Certificate;
import de.serversenke.lxd.client.core.model.CertificatePost;
import de.serversenke.lxd.client.core.model.CertificatePut;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdResponse;
import de.serversenke.lxd.client.core.rest.LxdCertificates;
import de.serversenke.lxd.client.core.test.util.TestData;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class MockCertificates extends Certificates {
	
	static final class MockLxdCertificates implements LxdCertificates {
		private final BehaviorDelegate<LxdCertificates> delegate;

		public MockLxdCertificates(BehaviorDelegate<LxdCertificates> delegate) {
			this.delegate = delegate;
		}

		@Override
		public Call<LxdResponse<String[]>> list() {
			LxdResponse<String[]> response = TestData.createResponse("sync", 200, "OK", "operation", new String[] {"cert 1", "cert 2"});
			return delegate.returningResponse(response).list();
		}

		@Override
		public Call<LxdResponse<Empty>> add(CertificatePost cert) {			
			LxdResponse<Certificate> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).add(cert);
		}

		@Override
		public Call<LxdResponse<Certificate>> get(String fingerprint) {
			Certificate c = new Certificate();
			c.setCertificate("certificate");
			c.setFingerprint(fingerprint);
			c.setName("name");
			c.setType("type");
			
			LxdResponse<Certificate> response = TestData.createResponse("sync", 200, "OK", "operation", c);
			return delegate.returningResponse(response).get(fingerprint);
		}

		@Override
		public Call<LxdResponse<Empty>> replaceProperties(String fingerprint, CertificatePut properties) {
			LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).replaceProperties(fingerprint, properties);
		}

		@Override
		public Call<LxdResponse<Empty>> updateProperties(String fingerprint, CertificatePut properties) {
			LxdResponse<Empty> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).updateProperties(fingerprint, properties);
		}

		@Override
		public Call<LxdResponse<Empty>> delete(String fingerprint) {
			LxdResponse<Certificate> response = TestData.createResponse("sync", 200, "OK", "operation", null);
			return delegate.returningResponse(response).delete(fingerprint);
		}
	}

	public MockCertificates(String url) throws Exception {
		super(url);

		NetworkBehavior behavior = NetworkBehavior.create();
		behavior.setErrorPercent(0);
		behavior.setDelay(0, TimeUnit.SECONDS);
		
		MockRetrofit mockRetrofit = new MockRetrofit.Builder(getRetrofit(url)).networkBehavior(behavior).build();

		BehaviorDelegate<LxdCertificates> delegate = mockRetrofit.create(LxdCertificates.class);
		MockLxdCertificates mockLxdCertificates = new MockLxdCertificates(delegate);
		
		lxdCertificates = mockLxdCertificates;
	}
}
