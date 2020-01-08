package iotsoa.iotsoaproject.models;

import java.util.Date;

public class MovementData {
	private Boolean movement;
	private Date date;
	private String room;
	private String stage;
	private String dpt;

	public MovementData(Boolean move, String room, String stage, String dpt) {
		super();
		this.movement = move;
		this.room = room;
		this.stage = stage;
		this.dpt = dpt;
		this.date= new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getSymptoms() {
		String res = "";
		if (getMovement() == true) {
			res += "THERE IS SOMEONE IN THE ROOM!!";
			System.out.println("ATTENTION");
		} else
			res += "NOBODY IN THE ROOM";
		return res;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDpt() {
		return dpt;
	}

	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
}
