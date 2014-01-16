package com.isales.print;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {

	private final static Properties properties;
	
	static {
		properties = new Properties();
		InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getPrintDir() {
		return properties.getProperty("print.dir");
	}
}
