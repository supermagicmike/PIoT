package fr.insa.Services.Lightmanagemnt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insa.Services.Lightmanagemnt.Service.LighteServiceDecision;
import fr.insa.Services.Lightmanagemnt.model.Data;
import fr.insa.Services.Lightmanagemnt.model.Decision;
 
@RestController
public class LightRessources {
	@GetMapping("/")
	public String welcome() {
		return "hello";
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST)
	public HashMap<String, String> getobject(@RequestBody String data) throws JsonMappingException, JsonProcessingException {
		Decision data_object = new ObjectMapper().readValue(data, Decision.class);
		LighteServiceDecision service = new LighteServiceDecision(data_object);
		System.out.println(service.getData().getSymptom());
		return service.state();
	}
	@RequestMapping(value = "/state/{dpt}/{etg}/{room}", method = RequestMethod.GET)
	public String getobject(@PathVariable Map<String, String> vals ) throws JsonMappingException, JsonProcessingException {
		LighteServiceDecision service = new LighteServiceDecision();
		return service.state().get(vals.get("dpt")+"_"+vals.get("etg")+"_"+vals.get("room"));
	}
	
	@RequestMapping(value = "/changeState/{dpt}/{etg}/{room}/{decision}", method = RequestMethod.GET)
	public void  state(@PathVariable Map<String, String> vals ) throws JsonMappingException, JsonProcessingException {
		LighteServiceDecision service = new LighteServiceDecision();
		service.force_admin(vals.get("dpt")+"_"+vals.get("etg")+"_"+vals.get("room"),vals.get("decision"));
	}

}
