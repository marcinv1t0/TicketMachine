import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by m1per on 16.09.2017.
 */
public class CurrencyProvider {
    private static final String COINS_FILEPATH = "./settings/coins.csv";

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
        initializeProvider();
        return availableCoins;
    }

    public void putCoins(BigDecimal coin, int count){
        initializeProvider();
        int currentCount = availableCoins.get(coin);
        currentCount += count;
        availableCoins.replace(coin, currentCount);
        CSVWriter writer = new CSVWriter();
        writer.writeMap(COINS_FILEPATH , availableCoins);
    }

    public void withdrawCoins(BigDecimal coin, int count){
        initializeProvider();
        int currentCount = availableCoins.get(coin);
        CSVWriter writer = new CSVWriter();
        currentCount -= count;
        availableCoins.replace(coin, currentCount);
        writer.writeMap(COINS_FILEPATH , availableCoins);
    }

    private void initializeProvider(){
        CSVReader reader = new CSVReader();
        List<String[]> content = reader.getCSVContent(COINS_FILEPATH);
        for (String[] line: content ) {
            availableCoins.put(new BigDecimal(line[0]), Integer.parseInt(line[1]));
        }
    }
}
