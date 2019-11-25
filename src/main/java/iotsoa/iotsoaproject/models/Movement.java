package iotsoa.iotsoaproject.models;

public class Movement {
	private boolean mvmt;

	public boolean isMvmt() {
		return mvmt;
	}

	public void setMvmt(boolean mvmt) {
		this.mvmt = mvmt;
	}
	
	public Movement(){
		
	}

	public Movement(boolean mvmt) {
		super();
		this.mvmt = mvmt;
	}
	public String toString(){
		return "Movement: "+this.mvmt;
	}
	

}
