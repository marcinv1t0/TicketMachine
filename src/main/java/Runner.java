import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by m1per on 14.09.2017.
 */
public class Runner {

    public static void main (String args[]){
        TicketProvider tickets = new TicketProvider();
        TicketPrinter printer = new TicketPrinter();

        for (Ticket ticket: tickets.getAllTickets()){
            printer.print(ticket);
        }

        DiscountCalculator discountCalculator = new DiscountCalculator();
        HashMap<BigDecimal, Integer> test = discountCalculator.calculateChange(new BigDecimal("5.0"), new BigDecimal("3.2"));

    }

}
