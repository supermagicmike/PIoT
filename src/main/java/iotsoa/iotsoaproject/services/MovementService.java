package iotsoa.iotsoaproject.services;

import org.springframework.stereotype.Service;
import iotsoa.iotsoaproject.models.MovementData;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class MovementService {
	private List<MovementData> movelist= new ArrayList<>(Arrays.asList(new MovementData(true),new MovementData(false)));
	
	public List<MovementData> getDatas(){
		return movelist;
	}
	public MovementData getMovementData(int index){
		return movelist.get(index);
	}
	public void addMovement(MovementData md){
	  movelist.add(md);

	}

}
