/**
 * Created by m1per on 13.09.2017.
 */
public class ShortTermTicket extends Ticket{
    private String validityTime;

    public ShortTermTicket(){
        super(0.0);
    }

    public ShortTermTicket(String validityTime, double price){
        super(price);
        this.validityTime = validityTime;
    }

    public String getPrintDetails() {
        String type;

        type = isReduced() ? "Reduced" : "Normal";

        return "Price: " + getPrice() + "\n"
                + "Validity: " + validityTime + "\n"
                + "Type: " + type;

    }
}
