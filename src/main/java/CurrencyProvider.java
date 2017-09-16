import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1per on 16.09.2017.
 */
public class CurrencyProvider {
    private List<BigDecimal> availableCurrency = new ArrayList<BigDecimal>(); //TODO : make sure it's sorted descending

    public CurrencyProvider(){
        initializeProvider();
    }

    public List<BigDecimal> getAvailableCurrency() {
        return availableCurrency;
    }

    private void initializeProvider(){
        availableCurrency.add(new BigDecimal("0.1"));
        availableCurrency.add(new BigDecimal("0.2"));
        availableCurrency.add(new BigDecimal("0.5"));
        availableCurrency.add(new BigDecimal("1.0"));
        availableCurrency.add(new BigDecimal("2.0"));
        availableCurrency.add(new BigDecimal("3.0"));
    }
}
