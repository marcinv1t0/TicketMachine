import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1per on 15.09.2017.
 */
public class TicketProvider {
    private List<Ticket> singleTickets = new ArrayList<Ticket>();
    private List<Ticket> shortTimeTickets = new ArrayList<Ticket>();


    public TicketProvider(){
        initializeList();
    }

    public List<Ticket> getSingleTickets() {
        return singleTickets;
    }

    public List<Ticket> getTimeTickets() {
        return shortTimeTickets;
    }

    public List<Ticket> getAllTickets(){
        List<Ticket> allTickets = singleTickets;
        allTickets.addAll(shortTimeTickets);
        return allTickets;

    }

    private void initializeList() {
        shortTimeTickets.add(new ShortTermTicket("30 min", new BigDecimal("3.0")));
        shortTimeTickets.add(new ShortTermTicket("60 min.", new BigDecimal("4.5")));
        shortTimeTickets.add(new ShortTermTicket("90 min.", new BigDecimal("6.0")));
        shortTimeTickets.add(new ShortTermTicket("24 hrs.", new BigDecimal("11.0")));

        singleTickets.add(new SingleTicket(false, new BigDecimal("3.0")));
        singleTickets.add(new SingleTicket(true, new BigDecimal("3.2")));
    }
}
