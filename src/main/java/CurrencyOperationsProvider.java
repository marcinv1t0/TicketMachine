import java.math.BigDecimal;
import java.util.*;

/**
 * Created by m1per on 14.09.2017.
 */
public class CurrencyOperationsProvider {

    public TreeMap<BigDecimal, Integer> calculateChange(BigDecimal inputAmount, BigDecimal summedPrice) {
        TreeMap<BigDecimal, Integer> currencyToReturn = new TreeMap<>();
        CurrencyProvider currencyProvider = new CurrencyProvider();
        TreeMap<BigDecimal, Integer> availableCurrency = currencyProvider.getCoinsWithCount();
        BigDecimal change;

        if (inputAmount.compareTo(summedPrice) == -1) {
            change = inputAmount;
        } else {
            change = inputAmount.subtract(summedPrice);

        }

        if (!availableCurrency.isEmpty()) {
            int count;
            for (Map.Entry<BigDecimal, Integer> curr : availableCurrency.entrySet()) {
                if (curr.getValue() > 0) {
                    count = (change.divide(curr.getKey(), BigDecimal.ROUND_HALF_UP)).intValue();
                    if (curr.getValue() < count) {
                        change = new BigDecimal(count);
                    }
                    change = change.subtract(curr.getKey().multiply(new BigDecimal(count)));
                    currencyToReturn.put(curr.getKey(), count);
                }
            }

        }
        return currencyToReturn;
    }

    public BigDecimal calculateChangeAmount(TreeMap<BigDecimal, Integer> currencyToReturn) {
        BigDecimal changeAmount = BigDecimal.ZERO;
        for (Map.Entry<BigDecimal, Integer> curr : currencyToReturn.entrySet()) {
            changeAmount = changeAmount.add((curr.getKey()).multiply(new BigDecimal(curr.getValue())));
        }
        return changeAmount;
    }

    public boolean isChangeFromAmountPossible(BigDecimal amount) {
        TreeMap<BigDecimal, Integer> possibleChange = calculateChange(amount, BigDecimal.ZERO);
        BigDecimal possibleChangeAmount = calculateChangeAmount(possibleChange);

        return amount.compareTo(possibleChangeAmount) == 0;
    }

    public BigDecimal calculatePrice(BigDecimal price, int count) {
        return price.multiply(new BigDecimal(count)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getReducedPrice(Ticket ticket, BigDecimal discount) {
        BigDecimal price;
        price = ticket.getPrice();
        return price.multiply(BigDecimal.ONE.subtract(discount)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
