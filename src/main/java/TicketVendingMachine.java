import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by m1per on 16.09.2017.
 */
public class TicketVendingMachine {
    private TicketProvider ticketProvider = new TicketProvider();
    private TicketPrinter ticketPrinter = new TicketPrinter();
    private CurrencyOperationsProvider currencyOperationsProvider = new CurrencyOperationsProvider();
    private CurrencyProvider currencyProvider = new CurrencyProvider();

    public List<Ticket> getSingleTickets() {
        return ticketProvider.getSingleTickets();
    }

    public List<Ticket> getTimeTickets() {
        return ticketProvider.getTimeTickets();
    }

    public List<Ticket> getAllTickets() { return ticketProvider.getAllTickets(); }

    public void print(Ticket ticket){ ticketPrinter.print(ticket); }

    public HashMap<BigDecimal, Integer> calculateChange(BigDecimal inputAmount, BigDecimal summedPrice){
        return currencyOperationsProvider.calculateChange(inputAmount, summedPrice);
    }

    public BigDecimal calculatePrice(Ticket ticket, int count) {
        return currencyOperationsProvider.calculatePrice(ticket, count);
    }

    public void applyDiscount(Ticket ticket, BigDecimal discount) {
        currencyOperationsProvider.applyDiscount(ticket, discount);
    }

    public List<BigDecimal> getAvailableCurrency() {
        return currencyProvider.getAvailableCurrency();
    }


}
