package fr.insa.Services.Windowsmanagemnt.Service;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insa.Services.Windowsmanagemnt.model.Decision;
import fr.insa.Services.Windowsmanagemnt.model.ServiceDecision;
import fr.insa.Services.Windowsmanagemnt.model.Time_decision;

public class WindowsServiceDecision {

	private Decision data;
	static HashMap<String, String> last_decision=new HashMap<>();
	Time_decision time;
	public WindowsServiceDecision(Decision data) throws JsonProcessingException {
		this.data = data;
		DataAanalyz();
	}
	public WindowsServiceDecision() {
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
			 if (data.getSymptom().equals("OK") && time.getHour()<22 ) {
					System.out.println(" RIEN FAIRE");
		 		}
				else if (data.getSymptom().equals("LOW")&& time.getHour()<22 && !last_decision.get(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom()).equals("close")) {
					System.out.println(" CLOSE THE WINDOWS");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"close","WINDOW");
					ObjectMapper obj = new ObjectMapper();
					String Ser =  obj.writeValueAsString(SD);
					DoPost(decision,Ser);
					last_decision.put(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "close");

		 		}
				else if (data.getSymptom().equals("HIGH") && time.getHour()<22 && !last_decision.get(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom()).equals("open") ) {
					System.out.println(" OPEN WINDOWS ");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"open","WINDOW");
					ObjectMapper obj = new ObjectMapper();
					String Ser =  obj.writeValueAsString(SD);
					DoPost(decision,Ser);
					last_decision.put(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "open");

				}
				else if (time.getHour()>=22 && !last_decision.get(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom()).equals("close") ) {
					System.out.println(" CLOSE WINDOWS ");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"close","WINDOW");
					ObjectMapper obj = new ObjectMapper();
					String Ser =  obj.writeValueAsString(SD);
					DoPost(decision,Ser);
					last_decision.put(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "close");
				}
		} else {
			 if (data.getSymptom().equals("OK") && time.getHour()<22 ) {
					System.out.println(" RIEN FAIRE");
		 		}
				else if (data.getSymptom().equals("LOW") ) {
					System.out.println(" CLOSE THE WINDOWS");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"close","WINDOW");
					ObjectMapper obj = new ObjectMapper();
					String Ser =  obj.writeValueAsString(SD);
					DoPost(decision,Ser);
					last_decision.putIfAbsent(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "close");

		 		}
				else if (data.getSymptom().equals("HIGH")   ) {
					System.out.println(" OPEN WINDOWS ");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"open","WINDOW");
 					ObjectMapper obj = new ObjectMapper();
					String Ser;
					try {
						Ser = obj.writeValueAsString(SD);
						DoPost(decision,Ser);

					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					last_decision.putIfAbsent(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "open");

				}
				else if (time.getHour()>=22) {
					System.out.println(" CLOSE WINDOWS ");
					ServiceDecision SD=new ServiceDecision(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(),"close","WINDOW");
					ObjectMapper obj = new ObjectMapper();
					String Ser;
					try {
						Ser = obj.writeValueAsString(SD);
						DoPost(decision,Ser);

					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					last_decision.putIfAbsent(data.getDpt()+"_"+data.getStage()+"_"+data.getRoom(), "close");
				}
		}
		
	}
	 public  void force_admin(String room , String decision){
		 if (last_decision.containsKey(room)) {
			 last_decision.put(room,decision);
		 }
		last_decision.putIfAbsent(room,decision);
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

	public HashMap<String, String> state() {
		return last_decision;
		
	}

}
