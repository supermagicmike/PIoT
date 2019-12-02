package iotsoa.iotsoaproject.services;

import org.springframework.stereotype.Service;

import iotsoa.iotsoaproject.models.DptData;
import iotsoa.iotsoaproject.models.Movement;
import iotsoa.iotsoaproject.models.RoomData;
import iotsoa.iotsoaproject.models.StageData;
import iotsoa.iotsoaproject.models.Temperature;
import iotsoa.iotsoaproject.models.TemperatureData;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class TemperatureService {
	//private static int LOWTEMP = 18;
	//private static int HIGHTEMP = 27;
	// private List<TemperatureData> templist= new
	// ArrayList<TemperatureData>(Arrays.asList(
	// new TemperatureData(12,20,"",""),new TemperatureData(12,28)));

	private HashMap<String, HashMap<String, HashMap<String, ArrayList<Temperature>>>> all = new HashMap<>();

	public void addTemperature(TemperatureData td) {
		try {
			/*
			 * ArrayList<Temperature> array; if (room.containsKey(td.getRoom()))
			 * { array = room.get(td.getRoom()); array.add(new
			 * Temperature(td.getTemp_extern(), td.getTemp_intern()));
			 * room.putIfAbsent(td.getRoom(),array); } else { array = new
			 * ArrayList<>(); array.add(new Temperature(td.getTemp_extern(),
			 * td.getTemp_intern())); room.putIfAbsent(td.getRoom(), array); }
			 * etage.putIfAbsent(td.getStage(), room);
			 * all.putIfAbsent(td.getDpt(), etage); array= null;
			 * System.out.println(all);
			 */
			if (all.containsKey(td.getDpt())) {
				if (all.get(td.getDpt()).containsKey(td.getStage())) {
					if (all.get(td.getDpt()).get(td.getStage()).containsKey(td.getRoom())) {
						all.get(td.getDpt()).get(td.getStage()).get(td.getRoom())
								.add(new Temperature(td.getTemp_extern(), td.getTemp_intern()));
					} else {
						ArrayList<Temperature> m = new ArrayList<>();
						m.add(new Temperature(td.getTemp_extern(), td.getTemp_intern()));
						all.get(td.getDpt()).get(td.getStage()).putIfAbsent(td.getRoom(), m);
					}
				}

				else {
					ArrayList<Temperature> m = new ArrayList<>();
					m.add(new Temperature(td.getTemp_extern(), td.getTemp_intern()));
					HashMap<String, ArrayList<Temperature>> room = new HashMap<>();
					room.putIfAbsent(td.getRoom(), m);
					HashMap<String, HashMap<String, ArrayList<Temperature>>> etage = new HashMap<>();
					all.get(td.getDpt()).putIfAbsent(td.getStage(), room);

				}
			}

			else {

				ArrayList<Temperature> m = new ArrayList<>();
				m.add(new Temperature(td.getTemp_extern(), td.getTemp_intern()));
				HashMap<String, ArrayList<Temperature>> room = new HashMap<>();
				room.putIfAbsent(td.getRoom(), m);
				HashMap<String, HashMap<String, ArrayList<Temperature>>> etage = new HashMap<>();
				etage.putIfAbsent(td.getStage(), room);
				all.putIfAbsent(td.getDpt(), etage);

			}

		} catch (Exception e) {
			System.out.println("erroor");
		}

		//System.out.println(all.toString());

	}
	public HashMap<String, HashMap<String, HashMap<String, ArrayList<Temperature>>>> getAll(){
		return all;
	}
	public HashMap<String, HashMap<String, ArrayList<Temperature>>> getStages(String dpt){
		return all.get(dpt) ;
	}
	public HashMap<String, ArrayList<Temperature>> getRoom(String dpt,String stage){
		return all.get(dpt).get(stage);
	}
	public ArrayList<Temperature> getTemperature(String dpt,String stage,String room){
		return all.get(dpt).get(stage).get(room);
	}



}
