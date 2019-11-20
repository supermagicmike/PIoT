package fr.insat.om2m.tp2.client;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({ Operations.class })
public class Operations {

	private String rn;
	private Boolean rr;
	private String api;

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public Boolean getRr() {
		return rr;
	}

	public void setRr(Boolean rr) {
		this.rr = rr;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

}
