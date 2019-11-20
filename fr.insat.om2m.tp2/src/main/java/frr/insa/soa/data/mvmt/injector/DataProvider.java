package frr.insa.soa.data.mvmt.injector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import obix.Bool;
import obix.Int;
import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

public class DataProvider implements PrepareData {

	@Override
	public int doPost(String resAddress, JSONObject resource) throws IOException {
		@SuppressWarnings("deprecation")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(resAddress);
		httpPost.setHeader("X-M2M-Origin", "admin:admin");
		httpPost.setHeader("Content-Type", "application/json;ty=4");
		HttpResponse httpResponse;
		try {
			httpPost.setEntity(new StringEntity(resource.toString()));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			httpResponse = httpClient.execute(httpPost);
			return httpResponse.getStatusLine().getStatusCode();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public int doGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print in String
		// Read JSON response and print
		JSONObject myResponse = new JSONObject(response.toString());
		JSONObject myResponse1 = new JSONObject((myResponse.get("current_condition")).toString());
		return (int) myResponse1.get("tmp");
	}

	@Override
	public void Datagenerator(String PostUrl, String GetUrl) throws JSONException, IOException {
		Random random = new Random();
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
		String strDate = dateFormat.format(date);
		ArrayList<String> rn_name = new ArrayList<>(Arrays.asList(PostUrl.split("/")));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Obj obj2 = new Obj();
				obj2.add(new Str("category", "Mvmt"));
				obj2.add(new Bool("Mvmt", Math.random() >= 0.5));
				obj2.add(new Str("dpt", "GEI"));
				obj2.add(new Str("stage", String.valueOf(i+1)));
				obj2.add(new Str("room", String.valueOf(j+1)));
				JSONObject obj = new JSONObject();
				obj = new JSONObject();
				obj.put("cnf", "application/json");
				obj.put("con", ObixEncoder.toString(obj2));
				obj.put("rn", new String(
						rn_name.get(rn_name.size() - 1) + "_" + rn_name.get(rn_name.size() - 2) + "_" + strDate));
				JSONObject resource = new JSONObject();
				resource.put("m2m:cin", obj);
				System.out.println(resource.toString());
				doPost(PostUrl + "GEI_" + (i + 1) + "_DATA/GEI_" + (i + 1) + "_DATA_" + (j + 1), resource);
			}

		}

	}
}
