import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by m1per on 16.09.2017.
 */
public class CurrencyProvider {
    private List<BigDecimal> availableCurrency = new ArrayList<BigDecimal>();

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
        availableCurrency.add(new BigDecimal("5.0"));
        Collections.sort(availableCurrency);
        Collections.reverse(availableCurrency);
    }
}
