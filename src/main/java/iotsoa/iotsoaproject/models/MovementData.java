package iotsoa.iotsoaproject.models;

public class MovementData {
	private Boolean movement;

public MovementData(Boolean move) {
		super();
		this.movement = move;
	
	}
public MovementData() {
		// TODO Auto-generated constructor stub
	}
public Boolean getMovement() {
		return movement;
	}
public void setMovement(Boolean move) {
		this.movement = move;
	}

public String getSymptoms(){
	String res="";
	if(getMovement()==true){
		res+="THERE IS SOMEONE IN THE ROOM!!";
		System.out.println("ATTENTION");
	}else res+="NOBODY IN THE ROOM";
	return res;
	}
}
