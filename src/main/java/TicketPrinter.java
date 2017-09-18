/**
 * Created by m1per on 14.09.2017.
 */
public class TicketPrinter {
    private int inkLevel = 100;

    public int getInkLevel() {
        return inkLevel;
    }

    public void setInkLevel(int inkLevel) {
        this.inkLevel = inkLevel;
    }

    public void print(Ticket ticket) {
        System.out.println("**");
        System.out.println(ticket);
        System.out.println("**");
        inkLevel--;
    }
}
