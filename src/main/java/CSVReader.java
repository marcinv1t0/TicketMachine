/**
 * Created by m1per on 17.09.2017.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static final String COMMA_DELIMITER = ",";

    public List<String[]> getCSVContent(String path){
        List<String[]> content = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {
                String[] singleInput = line.split(COMMA_DELIMITER);
                content.add(singleInput);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
