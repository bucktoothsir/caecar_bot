package my_caesar_bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public String token;
    public String username;
    InputStream inputStream;
    public Config(String fileName) throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
			}
            token = prop.getProperty("token");
            username = prop.getProperty("username");
            System.out.println("token: " + token);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        finally {
    		inputStream.close();
		}
    }
}
