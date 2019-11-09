package iotsoa.iotsoaproject.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iotsoa.iotsoaproject.models.TemperatureData;
import iotsoa.iotsoaproject.services.TemperatureService;



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
}
