import java.math.BigDecimal;
import java.util.*;

/**
 * Created by m1per on 14.09.2017.
 */
public class CurrencyOperationsProvider {

    public TreeMap<BigDecimal, Integer> calculateChange(BigDecimal inputAmount, BigDecimal summedPrice){ //DONE
        TreeMap<BigDecimal, Integer> currencyToReturn = new TreeMap<>();
        CurrencyProvider currencyProvider = new CurrencyProvider();
        TreeMap<BigDecimal, Integer> availableCurrency = currencyProvider.getCoinsWithCount();
        BigDecimal change;

        if (inputAmount.compareTo(summedPrice) == -1){
            change = inputAmount;
        }else{
            change = inputAmount.subtract(summedPrice);

        }

        if (!availableCurrency.isEmpty()){
            BigDecimal lowestDenomination = BigDecimal.valueOf(Double.MAX_VALUE);
            int count;
            for (Map.Entry<BigDecimal, Integer> curr : availableCurrency.entrySet()) {
                if (curr.getValue() > 0){
                    count = (change.divide(curr.getKey(), BigDecimal.ROUND_HALF_UP)).intValue();
                    if (curr.getValue() < count) { change = new BigDecimal(count); }
                    change = change.subtract(curr.getKey().multiply(new BigDecimal(count)));
                    currencyToReturn.put(curr.getKey(), count);
                }
            }

        }
        return currencyToReturn;
    }

    public BigDecimal calculateChangeAmount(TreeMap<BigDecimal, Integer> currencyToReturn){
        BigDecimal changeAmount = BigDecimal.ZERO;
        Iterator it = currencyToReturn.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            changeAmount = changeAmount.add(((BigDecimal) pair.getKey()).multiply(new BigDecimal((Integer) pair.getValue())));
        }
        return changeAmount;
    }

    /* Probalby has no point, to remove in future commits
    public boolean isChangeReturnPossible(){
        CurrencyProvider currencyProvider = new CurrencyProvider();
        TicketProvider ticketProvider = new TicketProvider();
        List<BigDecimal> availableCurrency = currencyProvider.getAvailableCoinInput();
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
    }*/

    public boolean isChangeFromAmountPossible(BigDecimal amount){
        TreeMap<BigDecimal, Integer> possibleChange = calculateChange(amount, BigDecimal.ZERO);
        BigDecimal possibleChangeAmount = calculateChangeAmount(possibleChange);

        return amount.compareTo(possibleChangeAmount) == 0;
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
