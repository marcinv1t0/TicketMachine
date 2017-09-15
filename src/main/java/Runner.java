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

    }

}
