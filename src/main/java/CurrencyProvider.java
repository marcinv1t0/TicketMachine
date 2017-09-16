import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1per on 16.09.2017.
 */
public class CurrencyProvider {
    private List<Double> availableCurrency = new ArrayList<Double>();

    public CurrencyProvider(){
        initializeProvider();
    }

    public List<Double> getAvailableCurrency() {
        return availableCurrency;
    }

    private void initializeProvider(){
        availableCurrency.add(0.1);
        availableCurrency.add(0.2);
        availableCurrency.add(0.5);
        availableCurrency.add(1.0);
        availableCurrency.add(2.0);
        availableCurrency.add(5.0);
    }
}
