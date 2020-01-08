package fr.insa.Services.Alarmmanagemnt.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		return hour;
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
}
