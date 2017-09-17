import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by m1per on 17.09.2017.
 */
public class CSVWriter {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public void universalWrite(String path, List<String[]> content) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(path);
            for (String[] line : content ) {
                for (int i = 0 ; i < line.length ; i++) {
                    fileWriter.append(line[i]);
                    if (i < line.length - 1){ fileWriter.append(COMMA_DELIMITER); }
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void writeMap(String path, TreeMap<BigDecimal, Integer> map) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(path);

            for (Map.Entry<BigDecimal , Integer> curr : map.entrySet()) {
                fileWriter.append(curr.getKey().toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(curr.getValue().toString());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
