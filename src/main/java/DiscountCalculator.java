import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by m1per on 14.09.2017.
 */
public class DiscountCalculator {

    public HashMap<BigDecimal, Integer> calculateChange(BigDecimal inputAmount, BigDecimal summedPrice){
        HashMap<BigDecimal, Integer> currencyToReturn = new HashMap<BigDecimal, Integer>();
        CurrencyProvider currencyProvider = new CurrencyProvider();
        List<BigDecimal> availableCurrency = currencyProvider.getAvailableCurrency();
        BigDecimal change = inputAmount.subtract(summedPrice);

        Collections.sort(availableCurrency);
        Collections.reverse(availableCurrency);

        if (!availableCurrency.isEmpty()){
            BigDecimal lowestDenomination = availableCurrency.get(0);
            int count;

            for (BigDecimal curr : availableCurrency) {
                count = (change.divide(curr, BigDecimal.ROUND_HALF_UP)).intValue();
                change = change.subtract(curr.multiply(new BigDecimal(count)));
                currencyToReturn.put(curr, count);
            }

        }
        return currencyToReturn;
    }

    public BigDecimal calculatePrice(Ticket ticket, int count){
        return ticket.getPrice().multiply(new BigDecimal(count));
    }

    public void applyDiscount(Ticket ticket, BigDecimal discount){
        BigDecimal price;

        price = ticket.getPrice();
        price = price.multiply(BigDecimal.ONE.subtract(discount));
        ticket.setPrice(price);
    }
}
