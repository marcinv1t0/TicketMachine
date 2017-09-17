import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by m1per on 16.09.2017.
 */
public class CurrencyProvider {
    private static TreeMap<BigDecimal, Integer> availableCurrency = new TreeMap<>(Collections.reverseOrder());

    public CurrencyProvider(){
        initializeProvider();
    }

    public List<BigDecimal> getAvailableCoinInput() {
        List<BigDecimal> availableCurrencyList = new ArrayList<>();
        availableCurrencyList.addAll(availableCurrency.keySet());
        return availableCurrencyList;
    }

    public TreeMap<BigDecimal, Integer> getCoinsWithCount(){
        return availableCurrency;
    }

    public void putCoins(BigDecimal coin, int count){
        int currentCount = availableCurrency.get(coin);
        currentCount += count;
        availableCurrency.replace(coin, currentCount);
    }

    public void withdrawCoins(BigDecimal coin, int count){
        int currentCount = availableCurrency.get(coin);
        currentCount -= count;
        availableCurrency.replace(coin, currentCount);
    }

    private void initializeProvider(){
        availableCurrency.put(new BigDecimal("0.1"), 10);
        availableCurrency.put(new BigDecimal("0.2"), 10);
        availableCurrency.put(new BigDecimal("0.5"), 10);
        availableCurrency.put(new BigDecimal("1.0"), 10);
        availableCurrency.put(new BigDecimal("2.0"), 10);
        availableCurrency.put(new BigDecimal("5.0"), 10);
    }
}
