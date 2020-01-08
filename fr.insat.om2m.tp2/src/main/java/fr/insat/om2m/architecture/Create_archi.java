package fr.insat.om2m.architecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;

import fr.insat.om2m.tp2.client.Response;
import fr.insat.om2m.tp2.mapper.Mapper;
import frr.insa.soa.data.temp.injector.ReadConfig;

public class Create_archi {
	ReadConfig s;
	String[] ae = { "Temperature_Sensor", "Mouvement_Sensor" };

	public Create_archi() throws IOException {
			s = new ReadConfig();
			HashMap<String, ArrayList<String>> m = s.getProperty();
 			String root = m.get("Root").get(0);
			for (int i = 0; i < ae.length; i++) {
				AE ae_Config = new AE();
				ae_Config.setAppName(ae[i]);
				ae_Config.setRequestReachability(true);
				ae_Config.setAppID(ae[i]+"_Data");
				ae_Config.setName(ae[i]);
				Response n_ae = create(root,new Mapper().marshal(ae_Config), "admin:admin", "application/xml;ty=2");
				System.out.println(n_ae.getStatusCode());
				if(n_ae.getStatusCode()==201||n_ae.getStatusCode()==409){
					Container cnt = new Container();
					for (int j = 0; j < 3; j++) {
						cnt.setName("GEI_" + (j+1)+"_DATA");
						Response n_cnt = create(root+ae[i],new Mapper().marshal(cnt),"admin:admin", "application/xml;ty=3");
						if(n_cnt.getStatusCode()==201||n_cnt.getStatusCode()==409){
							for (int k = 0; k < 3; k++) {
								cnt.setName("GEI_" + (j+1)+"_DATA_"+(k+1));
								Response n_cnt1 = create(root+ae[i]+"/"+"GEI_" + (j+1)+"_DATA",new Mapper().marshal(cnt),"admin:admin", "application/xml;ty=3");
								System.out.println(n_cnt1.getStatusCode());
							}
						}
					}
					cnt.setName("Description");
					Response n_cnt = create(root+ae[i],new Mapper().marshal(cnt),"admin:admin", "application/xml;ty=3");
					System.out.println(n_cnt.getStatusCode());
				}
			}
			
	}

	public Response create(String url, String representation, String originator, String type) throws IOException {
		Response response = new Response();
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", type);
		post.addHeader("X-M2M-Origin", originator);
		post.setEntity(new StringEntity(representation));
		try {
			CloseableHttpResponse reqResp = client.execute(post);
			response.setStatusCode(reqResp.getStatusLine().getStatusCode());
			response.setRepresentation(IOUtils.toString(reqResp.getEntity().getContent(), "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			client.close();
		}
		return response;
	}

	public static void main(String[] args) throws IOException {
		new Create_archi();
	}

}
