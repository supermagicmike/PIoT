package iotsoa.iotsoaproject.models;

public class TemperatureData {
private static int LOWTEMP = 18;
private static int HIGHTEMP=27;
private int temp_extern;
private int temp_intern;

public TemperatureData(int temp_extern, int temp_intern) {
	super();
	this.temp_extern = temp_extern;
	this.temp_intern = temp_intern;
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
public String getSymptoms(){
	String res="";
	if(getTemp_extern()<getTemp_intern()&&getTemp_extern()>LOWTEMP&&getTemp_extern()<HIGHTEMP){
		res+="OPEN WINDOW MAN";
		System.out.println("OPEN WINDOW MAN");
	}else res+="CLOSE WINDOW MAN";
	return res;
}


}
