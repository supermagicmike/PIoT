package iotsoa.iotsoaproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import iotsoa.iotsoaproject.models.Decision;
import iotsoa.iotsoaproject.models.Movement;
import iotsoa.iotsoaproject.models.MovementData;
import iotsoa.iotsoaproject.models.Temperature;
import iotsoa.iotsoaproject.models.TemperatureData;

public class DecisionController {
	private static int LOWTEMP = 18;
	private static int HIGHTEMP = 27;

public DecisionController() {
	super();
	// TODO Auto-generated constructor stub
}

public static String DecisontoJson(Decision decision){
	ObjectMapper mapper = new ObjectMapper();
	String s;
	try {
		s= mapper.writeValueAsString(decision);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		s=null;
	}
	return s;
}
public static Decision getTDecision(TemperatureData temp){
	String des;
	System.out.println("temp intern : "+temp.getTemp_intern());
	System.out.println("temp extern : "+temp.getTemp_extern());
	if (temp.getTemp_intern()<temp.getTemp_extern()){
		des="LOW";
	}else if(temp.getTemp_extern()<temp.getTemp_intern()&&temp.getTemp_extern()>=LOWTEMP&&temp.getTemp_extern()<=HIGHTEMP){
		des="HIGH";
	}else{
		des="OK";
	}
	System.out.println("Symptom :: "+des);
	return new Decision(des,"Temp",temp.getDpt(),temp.getStage(),temp.getRoom());
	
}
public static Decision getMDecision(MovementData mvmt){
	String des;
	if(mvmt.getMovement()){
		des="EXIST";
	}else{
		des="NOTEXIST";
	}
	return new Decision(des,"Mvmt",mvmt.getDpt(),mvmt.getStage(),mvmt.getRoom());
	
}
}
