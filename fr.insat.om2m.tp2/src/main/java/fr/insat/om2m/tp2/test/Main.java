package fr.insat.om2m.tp2.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;
import javax.xml.bind.JAXBException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.json.JSONObject;

import fr.insat.om2m.tp2.client.Client;
import fr.insat.om2m.tp2.client.Response;
import fr.insat.om2m.tp2.mapper.Mapper;
import fr.insat.om2m.tp2.util.RequestLoader;
import obix.Int;
import obix.Obj;
import obix.Str;
import obix.io.ObixDecoder;
import obix.io.ObixEncoder;

public class Main {

	private static String originator = "admin:admin";
	private static String cseProtocol = "http";
	private static String cseIp = "127.0.0.1";
	private static int csePort = 8080;
	private static String cseId = "in-cse";
	private static String cseName = "in-name";
	private static String accept = "application/xml";
	private static String Content_type = "application/xml";
	private static String url = cseProtocol + "://" + cseIp + ":" + Integer.toString(csePort) + "/~/" + cseId + "/";

	public static void main(String[] args) throws JAXBException, IOException {
		/*
		 * TP2 PARTIE 1
		 * 
		 * // rcn = 5
		 * 
		 * //get_test("?rcn=5");
		 * 
		 * // appTest AE //create_test("ae", "", "2");
		 * 
		 * // app-test get //get_test("?api=app-test");
		 * 
		 * //app-test update //update_test("in-name/app-test","ae");
		 * 
		 * //create cnt //create_test("cnt", "in-name/app-test", "3");
		 * 
		 * // app-test get cnt ressource //get_test("cnt-303721074");
		 * 
		 * //create cin //create_test("cin", "in-name/app-test/cntTest", "4");
		 * 
		 * // app-test get cnt ressource
		 * //get_test("in-name/app-test/cntTest/cinTest");
		 */

		/*
		 * TP2 PARTIE 2 OBIX
		 * 
		 */
		/*create obix
		create_test("ae", "in-name", "2");
		create_test("cnt", "CAE617626834", "3");
		create_obix();
		get_test_data("in-name/ae_617626834/cntTest/DATA_12_48_15");
*/
		create_obix();
		get_test_data("in-name/ae_617626834/cntTest/DATA_12_48_15");
	}

	public static void create_test(String gnt, String path, String ty) {
		RequestLoader rqstl = new RequestLoader();
		String presentation = RequestLoader.getRequestFromFile("create_" + gnt + ".xml");
		Response rsp = new Response();
		try {
			rsp = new Client().create(url + path, presentation, "admin:admin", "application/xml;ty=" + ty);
			System.out.println(rsp.toString());
		} catch (IOException e) {
			// TODOAuto-generated catch block
			e.printStackTrace();
		}
	}

	public static void update_test(String path, String gnt) {
		RequestLoader rqstl = new RequestLoader();
		String presentation = RequestLoader.getRequestFromFile("update_" + gnt + ".xml");
		Response rsp = new Response();
		try {
			System.out.println(url + path);
			rsp = new Client().update(url + path, presentation, originator);
			System.out.println(rsp.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void delete_test(String path) {
		Response rsp = new Client().delete(url + path, originator);
		System.out.println(rsp.toString());
	}

	public static void get_test(String variablepath) {
		try {
			System.out.println(url + "?" + variablepath);
			Response rsp = new Client().retrieve(url + variablepath, originator);
			System.out.println(rsp.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void get_test() {
		try {
			Response rsp = new Client().retrieve(url, originator);
			System.out.println(rsp.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void get_test_data(String URL) {
		try {
			Response rsp = new Client().retrieve(url+URL, originator);
			//Obj obj=ObixDecoder.fromString((String) new Mapper().unmarshal(rsp.getRepresentation()));
			ContentInstance cin = (ContentInstance) new Mapper().unmarshal(rsp.getRepresentation());
			System.out.println(cin.getContent()); 
			Obj obj =  ObixDecoder.fromString(cin.getContent());
			System.out.println(obj.get("temp_intern"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void create_obix() throws JAXBException, IOException {
		int sensorValue = (int) (Math.random() * 60);
		Random random = new Random();
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
		String strDate = dateFormat.format(date);
		JSONObject obj1 = new JSONObject();
		Obj obj2 = new Obj();
		obj2.add(new Str("category", "Temp"));
		obj2.add(new Int("temp_intern", random.nextInt(60 + 30) - 30));
		obj2.add(new Str("Unite", "C"));
		JSONObject obj = new JSONObject();
		obj = new JSONObject();
		obj.put("cnf", "application/text");
		obj.put("con", ObixEncoder.toString(obj2));
		String name ="DATA_" + strDate;
		obj.put("rn", name );
		JSONObject resource = new JSONObject();
		resource.put("m2m:cin", obj);
		doPost(url+"in-name/ae_617626834/cntTest/", resource);
		get_test_data("in-name/ae_617626834/cntTest/"+name);

	}
	public static int doPost(String resAddress, JSONObject resource) throws IOException {
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
}