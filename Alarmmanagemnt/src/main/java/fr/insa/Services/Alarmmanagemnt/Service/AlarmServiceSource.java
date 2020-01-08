package fr.insa.Services.Alarmmanagemnt.Service;

import fr.insa.Services.Alarmmanagemnt.model.Decision;
import fr.insa.Services.Alarmmanagemnt.model.Time_decision;

public class AlarmServiceSource {

	private Decision data;
	static String last_decision;

	public AlarmServiceSource(Decision data) {
		this.data = data;
	}
	public AlarmServiceSource() {
		 
	}

	public Decision getData() {
		return data;
	}

	public void setData(Decision data) {
		this.data = data;
	}
}
