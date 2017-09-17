import java.io.*;
import java.util.Properties;

/**
 * Created by m1per on 17.09.2017.
 */
public class PropertiesProvider {
    private static final String PROPERTIES_PATH = "./settings/config.properties";
    Properties prop = new Properties();
    InputStream input = null;
    OutputStream output = null;

    public void setProperty(String property, String value){

        try {

            output = new FileOutputStream(PROPERTIES_PATH);

            prop.setProperty(property, value);
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String getProperty(String property){

        try {

            input = new FileInputStream(PROPERTIES_PATH);
            prop.load(input);
            return prop.getProperty(property);

        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

