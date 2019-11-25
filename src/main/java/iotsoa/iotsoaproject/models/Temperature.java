package iotsoa.iotsoaproject.models;

public class Temperature {
	private int temp_extern;
	private int temp_intern;
	public Temperature(int temp_extern, int temp_intern) {
		super();
		this.temp_extern = temp_extern;
		this.temp_intern = temp_intern;
	}
	public String toString(){
		return "Temperature intern: "+this.temp_intern+" Temperature externe: "+this.temp_extern;
	}
}


