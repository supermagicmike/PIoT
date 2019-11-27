package iotsoa.iotsoaproject.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import iotsoa.iotsoaproject.controllers.utils.RestUtils;
import iotsoa.iotsoaproject.models.Movement;
import iotsoa.iotsoaproject.models.MovementData;
import iotsoa.iotsoaproject.models.Temperature;
import iotsoa.iotsoaproject.models.TemperatureData;
import iotsoa.iotsoaproject.services.MovementService;
import iotsoa.iotsoaproject.services.TemperatureService;
import obix.Obj;
import obix.io.ObixDecoder;

@RestController
public class DataController {
	
	@Autowired
	private TemperatureService tempService;
	@Autowired
	private MovementService mvmtService;

	@GetMapping("/hi")
	public String Hello() {
		return " Hello";
	}
	//TEMPERATURE//////
	@GetMapping("/temp")
	public HashMap<String, HashMap<String, HashMap<String, ArrayList<Temperature>>>> getTempDatas() throws JsonProcessingException {
		return  tempService.getAll();
	}
	
	@GetMapping("/temp/{dpt}")
	public HashMap<String, HashMap<String, ArrayList<Temperature>>> getTempStages(@PathVariable String dpt){
 		return tempService.getStages(dpt) ;
	}
	@GetMapping("/temp/{dpt}/{stage}")
	public HashMap<String, ArrayList<Temperature>> getTempRoom(@PathVariable String dpt,@PathVariable String stage){
		return tempService.getRoom(dpt, stage);
	}

	@GetMapping("/temp/{dpt}/{stage}/{room}")
	public ArrayList<Temperature> getTemperature(@PathVariable String dpt,@PathVariable String stage,@PathVariable String room) {
		return tempService.getTemperature(dpt, stage, room);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/temp")
	public void addTemperatureData(@RequestBody TemperatureData td) {
		System.out.println("TEMPERATURE :: :: " + td.toString());
		tempService.addTemperature(td);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Movement///////
	@GetMapping("/mvmt")
	public HashMap<String, HashMap<String, HashMap<String, ArrayList<Movement>>>> getMvmtDatas() {
		return mvmtService.getAll();
	}
	
	@GetMapping("/mvmt/{dpt}")
	public HashMap<String, HashMap<String, ArrayList<Movement>>> getMvmtStages(String dpt){
		return mvmtService.getStages(dpt) ;
	}
	@GetMapping("/mvmt/{dpt}/{stage}")
	public HashMap<String, ArrayList<Movement>> getMvmtRoom(String dpt,String stage){
		return mvmtService.getRoom(dpt, stage);
	}

	@GetMapping("/mvmt/{dpt}/{stage}/{room}")
	public ArrayList<Movement> getMovement(@PathVariable String dpt,@PathVariable String stage,@PathVariable String room) {
		return mvmtService.getMovement(dpt, stage, room);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/mvmt")
	public void addMovementData(@RequestBody MovementData td) {
		System.out.println("MOVEMENT :: :: " + td.toString());
		mvmtService.addMovement(td);
		;
	}
	/*@RequestMapping(method = RequestMethod.GET, value = "/Sub")
	public void doSubscribe() {
		JSONObject sub = new JSONObject();
		JSONObject attr = new JSONObject();
		try {
			attr.put("xmlns:m2m", RestUtils.getProps().getProperty("xmlns"));
			attr.put("nu", RestUtils.getProps().getProperty("nu"));
			attr.put("nct", RestUtils.getProps().getProperty("nct"));
			attr.put("rn", RestUtils.getProps().getProperty("rn"));
			sub.put("m2m:sub", attr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-M2M-Origin", RestUtils.getProps().getProperty("X-M2M-Origin"));
		headers.put("Content-Type", RestUtils.getProps().getProperty("Content-Type"));
		System.out.println("HEADERS :: " + headers.toString());
		System.out.println("Payload :: " + sub.toString());
		RestUtils.DoSub(RestUtils.getProps().getProperty("subaddress"), sub.toString(), headers);
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value = "/AllSub")
	public void doAllSubscribe(){
		JSONObject sub = new JSONObject();
		JSONObject attr = new JSONObject();
		try {
			attr.put("xmlns:m2m", RestUtils.getProps().getProperty("xmlns"));
			attr.put("nu", RestUtils.getProps().getProperty("nu"));
			attr.put("nct", RestUtils.getProps().getProperty("nct"));
			attr.put("rn", RestUtils.getProps().getProperty("rn"));
			sub.put("m2m:sub", attr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-M2M-Origin", RestUtils.getProps().getProperty("X-M2M-Origin"));
		headers.put("Content-Type", RestUtils.getProps().getProperty("Content-Type"));
		System.out.println("HEADERS :: " + headers.toString());
		System.out.println("Payload :: " + sub.toString());
		for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					String subadd=RestUtils.getProps().getProperty("Tsubaddress")+("GEI_" + (j+1)+"_DATA"+"/"+"GEI_" + (j+1)+"_DATA_"+(k+1));
					System.out.println("JE vais me subscribe à cette entitée : "+subadd);
					RestUtils.DoSub(subadd, sub.toString(), headers);
					subadd=RestUtils.getProps().getProperty("Msubaddress")+("GEI_" + (j+1)+"_DATA_"+(k+1));
					System.out.println("JE vais me subscribe à cette entitée : "+subadd);
					RestUtils.DoSub(subadd, sub.toString(), headers);
				}
			}
		}
		
		
	

	@RequestMapping(method = RequestMethod.POST, value = "/Cintemp")
	public void addCinData(@RequestBody String ci) throws JSONException {
		JSONObject resp = new JSONObject(ci);
		JSONObject resp1 = new JSONObject(resp.get("m2m:sgn").toString());
		JSONObject resp2 = new JSONObject(resp1.get("m2m:nev").toString());
		JSONObject resp3 = new JSONObject(resp2.get("m2m:rep").toString());
		JSONObject resp4 = new JSONObject(resp3.get("m2m:cin").toString());
		// System.out.println("DATA ::: " + resp4.toString());
		Obj s = ObixDecoder.fromString(resp4.get("con").toString());
		if (s.get("category").toString().equals("Temp")) {
			TemperatureData td = new TemperatureData();
			td.setTemp_extern(Integer.parseInt(s.get("temp_extern").toString()));
			td.setTemp_intern(Integer.parseInt(s.get("temp_intern").toString()));
			td.setDpt(s.get("dpt").toString());
			td.setStage(s.get("stage").toString());
			td.setRoom(s.get("room").toString());
			tempService.addTemperature(td);
			System.out.println(tempService.takeDecision(td));
		} else {
			if (s.get("category").toString().equals("Mvmt")) {
				MovementData mvmt = new MovementData();
				mvmt.setMovement(s.get("Mvmt").getBool());
				mvmt.setDpt(s.get("dpt").toString());
				mvmt.setStage(s.get("stage").toString());
				mvmt.setRoom(s.get("room").toString());
				mvmtService.addMovement(mvmt);
				System.out.println(s.get("Mvmt").getBool());
			}

		}

	}

}
