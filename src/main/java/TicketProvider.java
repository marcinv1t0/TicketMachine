import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1per on 15.09.2017.
 */
public class TicketProvider {
    private static final String SINGLE_TICKETS_FILEPATH = "./settings/singleTickets.csv";
    private static final String TIME_TICKETS_FILEPATH = "./settings/timeTickets.csv";


    private List<Ticket> singleTickets = new ArrayList<Ticket>();
    private List<Ticket> shortTimeTickets = new ArrayList<Ticket>();


    public TicketProvider() {
        initializeList();
    }

    public List<Ticket> getSingleTickets() {
        return singleTickets;
    }

    public List<Ticket> getTimeTickets() {
        return shortTimeTickets;
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> allTickets = singleTickets;
        allTickets.addAll(shortTimeTickets);
        return allTickets;
    }

    private void initializeList() {
        CSVReader reader = new CSVReader();
        List<String[]> content = reader.getCSVContent(TIME_TICKETS_FILEPATH);
        for (String[] line : content) {
            shortTimeTickets.add(new ShortTermTicket(line[0], new BigDecimal(line[1])));
        }

        content = new ArrayList<>(reader.getCSVContent(SINGLE_TICKETS_FILEPATH));
        for (String[] line : content) {
            singleTickets.add(new SingleTicket(Boolean.valueOf(line[0]), new BigDecimal(line[1])));
        }
    }
}
