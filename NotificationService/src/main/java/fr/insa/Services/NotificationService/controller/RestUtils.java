package fr.insa.Services.NotificationService.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestUtils {
	public static HttpResponse DoPost(String address, String payload,HashMap<String,String>headers){
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpPost post= new HttpPost(address);
		for (Entry<String, String> entry : headers.entrySet()) {
			post.setHeader(entry.getKey(), entry.getValue());
		} 
		try {
			post.setEntity(new StringEntity(payload));
			return httpClient.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static HttpResponse DoDelete(String address,HashMap<String,String>headers){
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpDelete delete= new HttpDelete(address);
		for (Entry<String, String> entry : headers.entrySet()) {
			delete.setHeader(entry.getKey(), entry.getValue());
		} 
		try {
			return httpClient.execute(delete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static HttpResponse DoSub(String address, String payload,HashMap<String,String>headers){
		DoPost(address, payload,headers);
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpPost sendOk=new HttpPost(address);
		try {
			return httpClient.execute(sendOk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
	}
	
	public static Properties getProps(){
		Properties prop = new Properties();
		try {
			InputStream input;
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return prop;
		
	}
	

}
