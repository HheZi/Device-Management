package sqlmanager;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static final Properties PROPERTIES = new Properties();
	
	static {
		try(var inputRes = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
			PROPERTIES.load(inputRes);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String get(String key) {
		return PROPERTIES.getProperty(key);
	}
	
}
