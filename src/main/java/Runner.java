/**
 * Created by m1per on 14.09.2017.
 */
public class Runner {

    public static void main (String args[]){
        TicketPrinter printer = new TicketPrinter();
        Ticket ticket1 = new SingleTicket(1.50, true);
        DiscountCalculator discountCalculator = new DiscountCalculator();

        printer.print(ticket1);
        ticket1 = new SingleTicket(1.50, false);
        printer.print(ticket1);

        discountCalculator.applyDiscount(ticket1, 0.35);
        printer.print(ticket1);

    }

}
