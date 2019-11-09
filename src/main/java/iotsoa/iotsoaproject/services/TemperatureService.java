package iotsoa.iotsoaproject.services;
import org.springframework.stereotype.Service;

import iotsoa.iotsoaproject.models.TemperatureData;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TemperatureService {
	private List<TemperatureData> templist= new ArrayList<TemperatureData>(Arrays.asList(
			new TemperatureData(12,20),new TemperatureData(12,28)));
	
	public List<TemperatureData> getDatas(){
		return templist;
	}
	public TemperatureData getTemperatureData(int index){
		return templist.get(index);
	}
	public void addTemperature(TemperatureData td){
		templist.add(td);
	}

}
