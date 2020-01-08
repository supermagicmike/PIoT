package fr.insa.Services.Lightmanagemnt.model;

public class ServiceDecision {
	String path;
	String decision;
	String type;
	
	public ServiceDecision() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceDecision(String path, String decision,String type) {
		super();
		this.path = path;
		this.decision = decision;
		this.type=type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ServiceDecision [path=" + path + ", decision=" + decision + "]";
	}
	
}
