package frr.insa.soa.data.mvmt.injector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OrganizeInjection implements Runnable {
	static HashMap<String, ArrayList<String>> Conf;
	private String SensorDB;

	public OrganizeInjection(String SensorDB) {
		this.SensorDB = SensorDB;
	}

	@Override
	public void run() {
		try {
			ReadConfig conf = new ReadConfig();
			DataProvider dataProvider = new DataProvider();
			Conf = conf.getProperty();
			for (int j = 0; j < 2; j++) {
				dataProvider.Datagenerator(this.SensorDB, Conf.get("ApiURL").get(0));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ReadConfig conf = new ReadConfig();
		Conf = conf.getProperty();
		Thread t;
		while (true) {
			for (int i = 0; i < Conf.get("Sensors").size(); i++) {
				t = new Thread(new OrganizeInjection(Conf.get("Sensors").get(i)));
				t.setName("Sensor Mvmt " + (i + 1));
				t.start();
				System.out.println(" start " + (t.getName()));
				t.currentThread().sleep(4000);

			}
			

		}

	}
}
