package frr.insa.soa.data.temp.injector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public interface PrepareData {
	public int doPost(String resAddress, JSONObject resource)
			throws UnsupportedEncodingException, ClientProtocolException, IOException;

	public int doGet(String url) throws MalformedURLException, IOException;

	public void Datagenerator(String PostUrl, String GetUrl, ArrayList<String> min, ArrayList<String> max)
			throws JSONException, IOException;

}
