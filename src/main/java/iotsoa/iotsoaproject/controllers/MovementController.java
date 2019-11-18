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
import iotsoa.iotsoaproject.models.MovementData;
import iotsoa.iotsoaproject.models.TemperatureData;
import iotsoa.iotsoaproject.services.MovementService;
import obix.Obj;
import obix.io.ObixDecoder;

@RestController
public class MovementController {
	@Autowired
	private MovementService service;
	
	@GetMapping("/hii")
	public String Hello (){
		return " Hii! ";
	}
	
	@GetMapping("/move")
	public List<MovementData> getDatas(){
		System.out.println("MOVE DATA:: "+service.getDatas().get(0));
		return service.getDatas();
	}
	
	@GetMapping("/move/{index}")
	public MovementData getMovementData(@PathVariable int index){
		return service.getMovementData(index);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/move")
	public void addMovementData(@RequestBody MovementData mv){
		System.out.println("Movement :: :: "+mv.toString());
		service.addMovement(mv);
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/MoveSub")
	public void doSubscribe(){
		JSONObject sub =new JSONObject();
		JSONObject attr=new JSONObject();
		try {
			attr.put("xmlns:m2m",RestUtils.getProps().getProperty("xmlns"));
			attr.put("nu",RestUtils.getProps().getProperty("nu"));
			attr.put("nct",RestUtils.getProps().getProperty("nct"));
			attr.put("rn",RestUtils.getProps().getProperty("rn"));//rn: Resource Name
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
	
	@RequestMapping(method=RequestMethod.POST, value="/Cinmove")
	public void addCinMovementData(@RequestBody String  ci) throws JSONException{
		MovementData mv =new MovementData();
		JSONObject resp = new JSONObject(ci);
		System.out.println(resp.toString());
		JSONObject resp1 = new JSONObject(resp.get("m2m:sgn").toString()); 
		JSONObject resp2 = new JSONObject(resp1.get("m2m:nev").toString()); 
		JSONObject resp3 = new JSONObject(resp2.get("m2m:rep").toString());
		JSONObject resp4 = new JSONObject(resp3.get("m2m:cin").toString()); 
		System.out.println("DATA ::: " + resp4.toString());
		Obj s = ObixDecoder.fromString( resp4.get("con").toString());
		mv.setMovement(Boolean.parseBoolean(s.get("movement").toString()));
		service.addMovement(mv);		
	}
	
}
