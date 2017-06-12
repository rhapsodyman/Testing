package httpclient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class HttpHelper {
	private static PoolingHttpClientConnectionManager connManager;
	private static CloseableHttpClient client;
	private static RequestConfig requestConfig;

	static {
		
		SSLContext context = null;
		try {
			context = SSLContexts.custom().loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}

	    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE))
	            .build();

	   connManager = new PoolingHttpClientConnectionManager(registry);
		
		connManager.setDefaultMaxPerRoute(5); // default is 2
		connManager.setMaxTotal(5); // default is 20

		client = HttpClients.custom().setConnectionManager(connManager).build();
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
	}

	public static void sendGet(String url, Map<String, String> headers) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);

		request.setConfig(requestConfig);

		if (headers != null) {
			for (Entry<String, String> pair : headers.entrySet()) {
				request.addHeader(pair.getKey(), pair.getValue());
			}
		}

		CloseableHttpResponse response = client.execute(request, HttpClientContext.create());

		System.out.println("Status code " + response.getStatusLine().getStatusCode());
		String respAsString = EntityUtils.toString(response.getEntity());
		// System.out.println("Resp " + respAsString);
		response.close();
	}

	public static void sendPost(String url, Map<String, String> headers, String body)
			throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);

		request.setConfig(requestConfig);
		request.setEntity(new StringEntity(body));

		if (headers != null) {
			for (Entry<String, String> pair : headers.entrySet()) {
				request.addHeader(pair.getKey(), pair.getValue());
			}
		}

		CloseableHttpResponse response = client.execute(request, HttpClientContext.create());

		System.out.println("Status code " + response.getStatusLine().getStatusCode());
		String respAsString = EntityUtils.toString(response.getEntity());
		// System.out.println("Resp " + respAsString);
		response.close();
	}

	public static void main(String[] args) throws InterruptedException {

		Thread[] threads = new Thread[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				try {
					sendGet("http://google.com", null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		new Thread(() -> {

			while (true) {
				System.out.println("Leased " + connManager.getTotalStats().getLeased());
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join(1000);
		}
	}
}
