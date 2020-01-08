package fr.insa.Services.Alarmmanagemnt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insa.Services.Alarmmanagemnt.Service.AlarmServiceSource;
import fr.insa.Services.Alarmmanagemnt.model.Decision;

@RestController
public class AlarmRessources {
	@GetMapping("/")
	public String welcome() {
		return "hello";
	}

	@RequestMapping(value = "/alarm", method = RequestMethod.POST)
	public String getobject(@RequestBody String data) throws JsonMappingException, JsonProcessingException {
		System.out.println(data);
		/*Decision data_object = new ObjectMapper().readValue(data, Decision.class);
		AlarmServiceSource service = new AlarmServiceSource(data_object);
		System.out.println(service.getData().getDpt());*/
		return null;
	}

}
