package iotsoa.iotsoaproject.models;

import java.util.Date;

public class TemperatureData {
	private int temp_extern;
	private int temp_intern;
	private String room;
	private String stage;
	private String dpt;
	private Date date;
	

	public TemperatureData(int temp_extern, int temp_intern, String room, String stage, String dpt) {
		super();
		this.temp_extern = temp_extern;
		this.temp_intern = temp_intern;
		this.room = room;
		this.stage = stage;
		this.dpt = dpt;
		this.setDate(new Date());
	}

	public TemperatureData() {
		// TODO Auto-generated constructor stub
	}

	public int getTemp_extern() {
		return temp_extern;
	}

	public void setTemp_extern(int temp_extern) {
		this.temp_extern = temp_extern;
	}

	public int getTemp_intern() {
		return temp_intern;
	}

	public void setTemp_intern(int temp_intern) {
		this.temp_intern = temp_intern;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}