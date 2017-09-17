import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by m1per on 16.09.2017.
 */
public class CurrencyProvider {
    private static TreeMap<BigDecimal, Integer> availableCoins = new TreeMap<>(Collections.reverseOrder());

    public CurrencyProvider(){
        initializeProvider();
    }

    public List<BigDecimal> getAvailableCoinInput() {
        List<BigDecimal> availableCurrencyList = new ArrayList<>();
        availableCurrencyList.addAll(availableCoins.keySet());
        return availableCurrencyList;
    }

    public TreeMap<BigDecimal, Integer> getCoinsWithCount(){
        return availableCoins;
    }

    public void putCoins(BigDecimal coin, int count){
        int currentCount = availableCoins.get(coin);
        currentCount += count;
        availableCoins.replace(coin, currentCount);
        CSVWriter writer = new CSVWriter();
        writer.writeMap("./settings/coins.csv" , availableCoins);
    }

    public void withdrawCoins(BigDecimal coin, int count){
        int currentCount = availableCoins.get(coin);
        CSVWriter writer = new CSVWriter();
        currentCount -= count;
        availableCoins.replace(coin, currentCount);
        writer.writeMap("./settings/coins.csv" , availableCoins);
    }

    private void initializeProvider(){
        CSVReader reader = new CSVReader();
        List<String[]> content = reader.getCSVContent("./settings/coins.csv");
        for (String[] line: content ) {
            availableCoins.put(new BigDecimal(line[0]), Integer.parseInt(line[1]));
        }
    }
}
