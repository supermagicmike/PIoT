package iotsoa.iotsoaproject.models;

 public class Temperature {
	private int temp_extern;
	private int temp_intern;
	public Temperature() {
		
	}
	public Temperature(int temp_extern, int temp_intern) {
		super();
		this.temp_extern = temp_extern;
		this.temp_intern = temp_intern;
	}
	public String toString(){
		return "Temperature intern: "+this.temp_intern+" Temperature externe: "+this.temp_extern;
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
}


