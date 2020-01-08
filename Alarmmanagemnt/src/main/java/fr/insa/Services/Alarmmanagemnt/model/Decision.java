package fr.insa.Services.Alarmmanagemnt.model;


import java.util.Date;

public class Decision {
	private String symptom;
	private String type;
	private String dpt;
	private String stage;
	private String room;
	private Date date;
	public Decision() {
		super();
		// TODO Auto-generated constructor stub
		
	}
	public Decision(String symptom,String type,String dpt,String stage, String room) {
		super();
		this.symptom = symptom;
		this.type=type;
		this.dpt=dpt;
		this.stage=stage;
		this.room=room;
		this.date= new Date();
	}
	public Decision(String symptom,Date date) {
		super();
		this.symptom = symptom;
		this.date=date;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDpt() {
		return dpt;
	}
	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
	

}