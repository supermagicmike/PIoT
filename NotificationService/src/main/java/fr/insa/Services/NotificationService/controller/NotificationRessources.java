package fr.insa.Services.NotificationService.controller;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.insa.Services.NotificationService.model.ServiceDecision;
import obix.Obj;
import obix.Str;
import obix.io.ObixEncoder;

@RestController
public class NotificationRessources {
	@GetMapping("/")
	public String welcome() {
		return "hello";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/Decision")
	public void GetandSendDecision(@RequestBody String data){
		try {
			System.out.println("ICI !!!!");
			ServiceDecision data_object = new ObjectMapper().readValue(data, ServiceDecision.class);
			System.out.println("data dec :"+data_object.getDecision()+" path  "+data_object.getPath()+" type :: "+data_object.getType());
			String address=RestUtils.getProps().getProperty("Action")+"/"+data_object.getType();
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("X-M2M-Origin", RestUtils.getProps().getProperty("X-M2M-Origin"));
			headers.put("Content-Type", "application/json;ty=4");
			Obj obj2= new Obj();
			obj2.add(new Str("Decision", data_object.getDecision()));
			JSONObject obj = new JSONObject();
			JSONObject resource = new JSONObject();
			obj = new JSONObject();
			obj.put("cnf", "application/text");
			obj.put("con", ObixEncoder.toString(obj2));
			obj.put("rn", data_object.getPath());
			resource.put("m2m:cin", obj);
			System.out.println(resource.toString());
			HttpResponse resp= RestUtils.DoPost(address, resource.toString(), headers);
			System.out.println("Response status :: " +resp.getStatusLine().getStatusCode());
			//System.out.println("Response body :: " +EntityUtils.toString(resp.getEntity()));
			String body =EntityUtils.toString(resp.getEntity());
			int code = resp.getStatusLine().getStatusCode();
			if (code==409)
			{
				System.out.println("I AM HEEEEEERE");
				RestUtils.DoDelete(address+"/"+data_object.getPath(), headers);
				RestUtils.DoPost(address, resource.toString(), headers);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
