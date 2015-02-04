package bliffoscope;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	
	//Function to read values from properties file
	public static String getValue(String key){
		Properties properties = new Properties();
		String currPath = new File("").getAbsolutePath();
		FileInputStream fip = null;
		try {
			fip = new FileInputStream(currPath+"/src/bliffoscope/files.properties");
			properties.load(fip);
			return properties.getProperty(key);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
