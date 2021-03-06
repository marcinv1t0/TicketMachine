import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by m1per on 16.09.2017.
 */
public class TicketVendingMachine {
    private TicketProvider ticketProvider = new TicketProvider();
    private TicketPrinter ticketPrinter = new TicketPrinter();
    private CurrencyOperationsProvider currencyOperationsProvider = new CurrencyOperationsProvider();
    private CurrencyProvider currencyProvider = new CurrencyProvider();
    private PropertiesProvider propertiesProvider = new PropertiesProvider();

    public TicketPrinter getTicketPrinter() {
        return ticketPrinter;
    }

    public List<Ticket> getSingleTickets() {
        return ticketProvider.getSingleTickets();
    }

    public List<Ticket> getTimeTickets() {
        return ticketProvider.getTimeTickets();
    }

    public List<Ticket> getAllTickets() {
        return ticketProvider.getAllTickets();
    }

    public void print(Ticket ticket) {
        ticketPrinter.print(ticket);
    }

    public TreeMap<BigDecimal, Integer> calculateChange(BigDecimal inputAmount, BigDecimal summedPrice) {
        return currencyOperationsProvider.calculateChange(inputAmount, summedPrice);
    }

    public BigDecimal calculatePrice(BigDecimal price, int count) {
        return currencyOperationsProvider.calculatePrice(price, count);
    }

    public BigDecimal getReducedPrice(Ticket ticket, BigDecimal discount) {
        return currencyOperationsProvider.getReducedPrice(ticket, discount);
    }

    public List<BigDecimal> getAvailableCoinInput() {
        return currencyProvider.getAvailableCoinInput();
    }

    public boolean isChangeFromAmountPossible(BigDecimal amount) {
        return currencyOperationsProvider.isChangeFromAmountPossible(amount);
    }

    public BigDecimal calculatePossibleChangeAmount(TreeMap<BigDecimal, Integer> currencyToReturn) {
        return currencyOperationsProvider.calculateChangeAmount(currencyToReturn);
    }

    public TreeMap<BigDecimal, Integer> getCoinsWithCount() {
        return currencyProvider.getCoinsWithCount();
    }

    public void putCoins(BigDecimal coin, int count) {
        currencyProvider.putCoins(coin, count);
    }

    public void withdrawCoins(BigDecimal coin, int count) {
        currencyProvider.withdrawCoins(coin, count);
    }

    public void returnChange(TreeMap<BigDecimal, Integer> coinsToReturn) {
        for (Map.Entry<BigDecimal, Integer> curr : coinsToReturn.entrySet()) {
            if (curr.getValue() > 0) {
                withdrawCoins(curr.getKey(), curr.getValue());
            }
        }
    }

    public String getDiscountValue() {
        return propertiesProvider.getProperty("discount");
    }

    public boolean isPrintPossible(int printCount) {
        return ticketPrinter.getInkLevel() >= printCount;
    }

    public int getInkLevel() {
        return ticketPrinter.getInkLevel();
    }

}
