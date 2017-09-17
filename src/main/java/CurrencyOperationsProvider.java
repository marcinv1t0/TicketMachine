import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by m1per on 14.09.2017.
 */
public class CurrencyOperationsProvider {

    public HashMap<BigDecimal, Integer> calculateChange(BigDecimal inputAmount, BigDecimal summedPrice){
        HashMap<BigDecimal, Integer> currencyToReturn = new HashMap<BigDecimal, Integer>();
        CurrencyProvider currencyProvider = new CurrencyProvider();
        List<BigDecimal> availableCurrency = currencyProvider.getAvailableCurrency();
        BigDecimal change;

        if (inputAmount.compareTo(summedPrice) == -1){
            change = inputAmount;
        }else{
            change = inputAmount.subtract(summedPrice);

        }

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

    public BigDecimal calculatePossibleChangeAmount(HashMap<BigDecimal, Integer> currencyToReturn){
        BigDecimal changeAmount = BigDecimal.ZERO;
        Iterator it = currencyToReturn.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            changeAmount = changeAmount.add(((BigDecimal) pair.getKey()).multiply(new BigDecimal((Integer) pair.getValue())));
            //TODO deep copy if it.remove() needed
        }
        return changeAmount;
    }

    public boolean isChangeReturnPossible(){
        CurrencyProvider currencyProvider = new CurrencyProvider();
        TicketProvider ticketProvider = new TicketProvider();
        List<BigDecimal> availableCurrency = currencyProvider.getAvailableCurrency();
        List<Ticket> availableTickets = ticketProvider.getAllTickets();
        BigDecimal smallestDenomination;
        Ticket reducedTicket;

        if (!availableCurrency.isEmpty()){
            smallestDenomination = availableCurrency.get(0);
        }else{
            return false;
        }

        for (BigDecimal value : availableCurrency ){
            smallestDenomination = (value.compareTo(smallestDenomination) == -1) ? value : smallestDenomination;
        }

        for (Ticket ticket : availableTickets ) {
            if (ticket.getPrice().remainder(smallestDenomination).compareTo(BigDecimal.ZERO) == 1){
                return false;
            }
            applyDiscount(ticket, new BigDecimal("0.35"));
            if (ticket.getPrice().remainder(smallestDenomination).compareTo(BigDecimal.ZERO) == 1){
                return false;
            }
        }
        return true;
    }

    public boolean isChangeFromAmountPossible(BigDecimal amount){
        BigDecimal smallestDenomination;
        CurrencyProvider currencyProvider = new CurrencyProvider();
        List<BigDecimal> availableCurrency = currencyProvider.getAvailableCurrency();

        if (!availableCurrency.isEmpty()){
            smallestDenomination = availableCurrency.get(0);
        }else{
            return false;
        }
        for (BigDecimal value : availableCurrency ){
            smallestDenomination = (value.compareTo(smallestDenomination) == -1) ? value : smallestDenomination;
        }
        if (amount.remainder(smallestDenomination).compareTo(BigDecimal.ZERO) == 1){
            return false;
        }
        return true;
    }

    public BigDecimal calculatePrice(Ticket ticket, int count){
        return ticket.getPrice().multiply(new BigDecimal(count)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void applyDiscount(Ticket ticket, BigDecimal discount){
        BigDecimal price;

        price = ticket.getPrice();
        price = price.multiply(BigDecimal.ONE.subtract(discount)).setScale(2, BigDecimal.ROUND_HALF_UP);
        ticket.setPrice(price);
    }
}
