package iotsoa.iotsoaproject.models;

import java.util.Date;

public class Movement {
	private boolean mvmt;
	private Date date;

	public boolean isMvmt() {
		return mvmt;
	}

	public void setMvmt(boolean mvmt) {
		this.mvmt = mvmt;
	}
	
	public Movement(){
		this.date=new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Movement(boolean mvmt) {
		super();
		this.mvmt = mvmt;
		this.date=new Date();
	}
	public String toString(){
		return "Movement: "+this.mvmt;
	}
	

}
