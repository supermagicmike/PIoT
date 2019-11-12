package iotsoa.iotsoaproject.controllers;


import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iotsoa.iotsoaproject.controllers.utils.RestUtils;
import iotsoa.iotsoaproject.models.TemperatureData;
import iotsoa.iotsoaproject.services.TemperatureService;
import obix.Obj;
import obix.io.ObixDecoder;




@RestController
public class TemperatureController {
	@Autowired
	private TemperatureService service;
	
	@GetMapping("/hi")
	public String Hello (){
		return " Hello";
	}
	
	@GetMapping("/temp")
	public List<TemperatureData> getDatas(){
		System.out.println("TEMP DATA:: "+service.getDatas().get(0));
		return service.getDatas();
	}
	@GetMapping("/temp/{index}")
	public TemperatureData getTemperatureData(@PathVariable int index){
		return service.getTemperatureData(index);
	}
	@RequestMapping(method=RequestMethod.POST, value="/temp")
	public void addTemperatureData(@RequestBody TemperatureData td){
		System.out.println("TEMPERATURE :: :: "+td.toString());
		service.addTemperature(td);;		
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/TempSub")
	public void doSubscribe(){
		JSONObject sub =new JSONObject();
		JSONObject attr=new JSONObject();
		try {
			attr.put("xmlns:m2m",RestUtils.getProps().getProperty("xmlns"));
			attr.put("nu",RestUtils.getProps().getProperty("nu"));
			attr.put("nct",RestUtils.getProps().getProperty("nct"));
			attr.put("rn",RestUtils.getProps().getProperty("rn"));
			sub.put("m2m:sub", attr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,String> headers=new HashMap<String, String>();
		headers.put("X-M2M-Origin", RestUtils.getProps().getProperty("X-M2M-Origin"));
		headers.put("Content-Type", RestUtils.getProps().getProperty("Content-Type"));
		System.out.println("HEADERS :: "+headers.toString());
		RestUtils.DoSub(RestUtils.getProps().getProperty("subaddress"),sub.toString(), headers);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/Cintemp")
	public void addCinTemperatureData(@RequestBody String  ci) throws JSONException{
		TemperatureData td =new TemperatureData();
		JSONObject resp = new JSONObject(ci);
		JSONObject resp1 = new JSONObject(resp.get("m2m:sgn").toString()); 
		JSONObject resp2 = new JSONObject(resp1.get("m2m:nev").toString()); 
		JSONObject resp3 = new JSONObject(resp2.get("m2m:rep").toString());
		JSONObject resp4 = new JSONObject(resp3.get("m2m:cin").toString()); 
		System.out.println("DATA ::: " + resp4.toString());
		Obj s = ObixDecoder.fromString( resp4.get("con").toString());
		td.setTemp_extern(Integer.parseInt(s.get("temp_extern").toString()));
		td.setTemp_intern(Integer.parseInt(s.get("temp_intern").toString()));	
		service.addTemperature(td);		
	}
	
}
