package frr.insa.soa.data.mvmt.injector;

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


	void Datagenerator(String PostUrl, String GetUrl) throws JSONException, IOException;

}
