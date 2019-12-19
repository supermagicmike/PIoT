package fr.insa.Services.Doormanagemnt.Service;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insa.Services.Doormanagemnt.model.Decision;
import fr.insa.Services.Doormanagemnt.model.ServiceDecision;
import fr.insa.Services.Doormanagemnt.model.Time_decision;

public class DoorServiceDecision {

	private Decision data;
	static HashMap<String, String> last_decision=new HashMap<>();
	Time_decision time;
 
	public DoorServiceDecision(Decision data) throws JsonProcessingException {
		this.data = data;
		DataAanalyz() ;
	}
	public DoorServiceDecision() {
		 
	}

	public Decision getData() {
		return data;
	}

	public void setData(Decision data) {
		this.data = data;
	}
	protected void DataAanalyz() throws JsonProcessingException {
		 time = new Time_decision();
		 String alarm = RestUtils.getProps().getProperty("Alarm");
		 String decision = RestUtils.getProps().getProperty("Decision");
		 if (last_decision.containsKey(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom())) {
			 if (data.getSymptom().equals("NOTEXIST") && time.getHour()>=22 && !last_decision.get(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom()).equals("close")) {
					System.out.println(" no body gt 22  ==>close");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"close","DOOR");
					ObjectMapper obj = new ObjectMapper();
					String Ser =  obj.writeValueAsString(SD);
					DoPost(decision,Ser);
					last_decision.put(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "close");
				}
				else if (data.getSymptom().equals("EXIST") && time.getHour() >= 22 && !last_decision.get(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom()).equals(alarm) ) {
			        ObjectMapper Obj = new ObjectMapper(); 
			        String notification = "Someone in the room "+data.getRoom()+" of departement "+ data.getDpt()+" stage"+data.getStage() +"we can't close the door";
					DoPost(alarm, notification);
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),alarm,"DOOR");
					ObjectMapper obj = new ObjectMapper();
					String Ser =  obj.writeValueAsString(SD);
					DoPost(decision,Ser);
					last_decision.put(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), alarm);
				}
				
		} else {
			if (data.getSymptom().equals("NOTEXIST") && time.getHour()>=22 ) {
				System.out.println(" no body gt 22  ==>close");
				last_decision.putIfAbsent(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "close");
				ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"close","DOOR");
				ObjectMapper obj = new ObjectMapper();
				String Ser =  obj.writeValueAsString(SD);
				DoPost(decision,Ser);
			}
			else if (data.getSymptom().equals("EXIST") && time.getHour() >= 22  ) {
		        ObjectMapper Obj = new ObjectMapper(); 
		        String notification = "Someone in the room "+data.getRoom()+" of departement "+ data.getDpt()+" stage"+data.getStage() +"we can't close the door";
				DoPost(alarm, notification);
				ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),alarm,"DOOR");
				ObjectMapper obj = new ObjectMapper();
				String Ser =  obj.writeValueAsString(SD);
				DoPost(decision,Ser);
				last_decision.putIfAbsent(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), alarm);
			}
		}
	}
	
	
	 public  void force_admin(String room , String decision){
		 if (last_decision.containsKey(room)) {
			 last_decision.put(room,decision);
		 }
		last_decision.putIfAbsent(room,decision);
	}
	
	public HashMap<String, String> state() {
		return last_decision;
		
	}
	
	public static HttpResponse DoPost(String address, String payload){
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpPost post= new HttpPost(address);
		try {
			post.setEntity(new StringEntity(payload));
			return httpClient.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
