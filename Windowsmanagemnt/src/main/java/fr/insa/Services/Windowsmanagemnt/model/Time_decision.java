package fr.insa.Services.Windowsmanagemnt.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Time_decision {
	private int hour;
	private int minute;
	private int seconde;



	public Time_decision() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.setHour(now.getHour());
		this.setMinute(now.getMinute());
		this.setSeconde(now.getSecond());
	}
	
	

	public int getHour() {
		Properties props = getProps();
 		if (props.getProperty("time")!=null) {
			return Integer.parseInt((String) props.get("time"));
		} else {
			return hour;

		}
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSeconde() {
		return seconde;
	}

	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}
	
	public static Properties getProps(){
		Properties prop = new Properties();
		try {
			InputStream input;
			input = new FileInputStream("Service_config.proprities");
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return prop;
	}
}
