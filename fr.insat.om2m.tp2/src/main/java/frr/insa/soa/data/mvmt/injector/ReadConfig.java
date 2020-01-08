package frr.insa.soa.data.mvmt.injector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ReadConfig {
	Properties configFile;
	static HashMap<String, ArrayList<String>> mymap;

	public ReadConfig() throws IOException {
		configFile = new java.util.Properties();
		configFile.load(this.getClass().getClassLoader().getResourceAsStream("Config.properties "));
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}

	public HashMap<String, ArrayList<String>> getProperty() {
		mymap = new HashMap<>();
		for (String key : configFile.stringPropertyNames()) {
			if (!key.equals("Sensors") &&  !key.equals("min_sensors")  && !key.equals("max_sensors")) {
				ArrayList<String> tmp = new ArrayList<>();
				tmp.add(configFile.getProperty(key));
				mymap.put(key, tmp);
			} else {
				List<String> URL = new ArrayList<>(Arrays.asList(configFile.getProperty(key).split(";")));
				mymap.put(key, (ArrayList<String>) URL);
			}

		}
		System.out.println(mymap);
		return mymap;
	}

}