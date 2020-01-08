package fr.insat.om2m.tp2.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import fr.insat.om2m.tp2.util.RequestLoader;

public class Client implements ClientInterface {

	public Response retrieve(String url, String originator) throws IOException {
		Response response = new Response();
		// Instantiate a new Client
		CloseableHttpClient client = HttpClients.createDefault();
		// Instantiate the correct Http Method
		HttpGet get = new HttpGet(url);
		// add headers
		// TODO
		get.addHeader("Accept", "application/xml");
		get.addHeader("X-M2M-Origin", originator);
		try {
			// send request
			CloseableHttpResponse reqResp = client.execute(get);
			response.setStatusCode(reqResp.getStatusLine().getStatusCode());
			response.setRepresentation(IOUtils.toString(reqResp.getEntity().getContent(), "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			client.close();
		}
		// return response
		return response;
	}

	public Response create(String url, String representation, String originator, String type) throws IOException {
		Response response = new Response();
		// implementation of post methode
		// Instantiate a new Client
		CloseableHttpClient client = HttpClients.createDefault();
		// Instantiate the correct Http Method
		HttpPost post = new HttpPost(url);
		// add headers
		// TODO
		post.addHeader("Content-Type", type);
		post.addHeader("X-M2M-Origin", originator);
		post.setEntity(new StringEntity(representation));
		try {
			// send request
			CloseableHttpResponse reqResp = client.execute(post);
			response.setStatusCode(reqResp.getStatusLine().getStatusCode());
			response.setRepresentation(IOUtils.toString(reqResp.getEntity().getContent(), "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			client.close();
		}
		return response;
	}

	public Response update(String url, String representation, String originator) throws IOException {
		Response response = new Response();
		// implementation of post methode
		// Instantiate a new Client
		CloseableHttpClient client = HttpClients.createDefault();
		// Instantiate the correct Http Method
		HttpPut put  = new HttpPut(url);
		// add headers
		// TODO
		put.addHeader("Content-Type", "application/xml");
		put.addHeader("X-M2M-Origin", originator);
		put.setEntity(new StringEntity(representation));
		try {
			// send request
			CloseableHttpResponse reqResp = client.execute(put);
			response.setStatusCode(reqResp.getStatusLine().getStatusCode());
			response.setRepresentation(IOUtils.toString(reqResp.getEntity().getContent(), "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			client.close();
		}
		return response;
	}

	public Response delete(String url, String originator) {
		Response response = new Response();
		// Instantiate a new Client
		CloseableHttpClient client = HttpClients.createDefault();
		// Instantiate the correct Http Method
		HttpDelete delete = new HttpDelete(url);
		// add headers
		// TODO
		delete.addHeader("Content-Type", "application/xml");
		delete.addHeader("X-M2M-Origin", originator);
		try {
			// send request
			CloseableHttpResponse reqResp = client.execute(delete);
			response.setStatusCode(reqResp.getStatusLine().getStatusCode());
			response.setRepresentation(IOUtils.toString(reqResp.getEntity().getContent(), "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// return response
		return response;
	}

}
