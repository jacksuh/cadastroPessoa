package br.com.jackson.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@ApplicationScoped
public class HttpClientService {
	 private CloseableHttpClient client;

	    @PostConstruct
	    private void init() {
	        client = HttpClients.createDefault();
	    }

	    @PreDestroy
	    private void destroy() {
	        try {
	            client.close();
	        } catch (IOException e) {
	            // loga se quiser
	        }
	    }

	    public String get(String url) {
	        HttpGet get = new HttpGet(url);

	        try (CloseableHttpResponse response = client.execute(get)) {
	            int status = response.getStatusLine().getStatusCode();

	            if (status != 200) {
	                throw new RuntimeException("Erro ao chamar API. Status: " + status);
	            }

	            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
	        } catch (IOException e) {
	            throw new RuntimeException("Erro de IO ao chamar API: " + e.getMessage(), e);
	        }
	    }
		
}
