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
        shortTimeTickets.add(new ShortTermTicket("30 min", 3.0));
        shortTimeTickets.add(new ShortTermTicket("60 min.", 4.5));
        shortTimeTickets.add(new ShortTermTicket("90 min.", 6.0));
        shortTimeTickets.add(new ShortTermTicket("24 hrs.", 11.0));

        singleTickets.add(new SingleTicket(false, 3.0));
        singleTickets.add(new SingleTicket(true, 3.2));
    }
}
