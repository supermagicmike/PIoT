package iotsoa.iotsoaproject.services;

import org.springframework.stereotype.Service;
import iotsoa.iotsoaproject.models.MovementData;
import iotsoa.iotsoaproject.models.Movement;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class MovementService {
	private HashMap<String, HashMap<String, HashMap<String, ArrayList<Movement>>>> all = new HashMap<>();

	public void addMovement(MovementData td) {
		try {
			/*
			 * ArrayList<Movement> array; if (room.containsKey(td.getRoom()))
			 * { array = room.get(td.getRoom()); array.add(new
			 * Movement(td.getTemp_extern(), td.getTemp_intern()));
			 * room.putIfAbsent(td.getRoom(),array); } else { array = new
			 * ArrayList<>(); array.add(new Movement(td.getTemp_extern(),
			 * td.getTemp_intern())); room.putIfAbsent(td.getRoom(), array); }
			 * etage.putIfAbsent(td.getStage(), room);
			 * all.putIfAbsent(td.getDpt(), etage); array= null;
			 * System.out.println(all);
			 */
			if (all.containsKey(td.getDpt())) {
				if (all.get(td.getDpt()).containsKey(td.getStage())) {
					if (all.get(td.getDpt()).get(td.getStage()).containsKey(td.getRoom())) {
						all.get(td.getDpt()).get(td.getStage()).get(td.getRoom())
								.add(new Movement(td.getMovement()));
					} else {
						ArrayList<Movement> m = new ArrayList<>();
						m.add(new Movement(td.getMovement()));
						all.get(td.getDpt()).get(td.getStage()).putIfAbsent(td.getRoom(), m);
					}
				}

				else {
					ArrayList<Movement> m = new ArrayList<>();
					m.add(new Movement(td.getMovement()));
					HashMap<String, ArrayList<Movement>> room = new HashMap<>();
					room.putIfAbsent(td.getRoom(), m);
				//	HashMap<String, HashMap<String, ArrayList<Movement>>> etage = new HashMap<>();
					all.get(td.getDpt()).putIfAbsent(td.getStage(), room);

				}
			}

			else {

				ArrayList<Movement> m = new ArrayList<>();
				m.add(new Movement(td.getMovement()));
				HashMap<String, ArrayList<Movement>> room = new HashMap<>();
				room.putIfAbsent(td.getRoom(), m);
				HashMap<String, HashMap<String, ArrayList<Movement>>> etage = new HashMap<>();
				etage.putIfAbsent(td.getStage(), room);
				all.putIfAbsent(td.getDpt(), etage);

			}

		} catch (Exception e) {
			System.out.println("erroor");
		}

		//System.out.println(all.toString());

	}
	public  HashMap<String, HashMap<String, HashMap<String, ArrayList<Movement>>>> getAll(){
		return all;
	}
	public HashMap<String, HashMap<String, ArrayList<Movement>>> getStages(String dpt){
		return all.get(dpt) ;
	}
	public HashMap<String, ArrayList<Movement>> getRoom(String dpt,String stage){
		return all.get(dpt).get(stage);
	}
	public ArrayList<Movement> getMovement(String dpt,String stage,String room){
		return all.get(dpt).get(stage).get(room);
	}


}
