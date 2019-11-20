package fr.insat.om2m.tp2.test;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.ContentInstance;

import fr.insat.om2m.tp2.client.Client;
import fr.insat.om2m.tp2.client.Response;
import fr.insat.om2m.tp2.mapper.Mapper;
import fr.insat.om2m.tp2.mapper.MapperInterface;
import fr.insat.om2m.tp2.util.RequestLoader;

public class MapperTest {
	static MapperInterface mapper = new Mapper();
	private static String originator = "admin:admin";
	private static String cseProtocol = "http";
	private static String cseIp = "127.0.0.1";
	private static int csePort = 8080;
	private static String cseId = "in-cse";
	private static String cseName = "in-name";
	private static String accept = "application/xml";
	private static String Content_type = "application/xml";
	private static String url = cseProtocol + "://" + cseIp + ":" + Integer.toString(csePort) + "/~/" + cseId + "/";

	public static void main(String[] args) throws JAXBException {

		// example to test marshal operation
		
		//create_test("", "2");
		
		// TODO test unmarshal
		get_test("?api=app-test");
		// get the XML representation, parse it with unmarshal operation
	}
	
	
	public static void create_test(String path, String ty) {
		RequestLoader rqstl = new RequestLoader();
		AE ae = new AE();
		ae.setRequestReachability(false); 
		ae.setAppID("testing1");
		ae.setAppName("testing");
		String presentation = mapper.marshal(ae);
		System.out.println(presentation);
		Response rsp = new Response();
		try {
			rsp = new Client().create(url + path, presentation, "admin:admin", "application/xml;ty=" + ty);
			System.out.println(rsp.toString());
		} catch (IOException e) {
			// TODOAuto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void get_test(String variablepath) throws JAXBException {
		try {
			System.out.println(url + "?" + variablepath);
			Response rsp = new Client().retrieve("http://127.0.0.1:8080/~/in-cse/in-name?api=app-test", originator);
			ContentInstance xml_mapper = (ContentInstance) mapper.unmarshal(rsp.getRepresentation());
			// System.out.println(xml_mapper.getContent());
			System.out.println(xml_mapper.getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
