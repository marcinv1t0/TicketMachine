/**
 * Created by m1per on 13.09.2017.
 */
public class ShortTermTicket extends Ticket{
    private ValidityTime validityTime;

    public ShortTermTicket(){
        super(0.0);
    }

    public ShortTermTicket(double price, ValidityTime validityTime){
        super(price);
        this.validityTime = validityTime;
    }

    public String getPrintDetails() {
        String validity, type;

        switch (validityTime) {
            case M30:
                validity = "30 min.";
                break;
            case M60:
                validity = "60 min.";
                break;
            case M90:
                validity = "90 min.";
                break;
            case H24:
                validity = "24 hrs";
                break;
            default:
                validity = "";
                break;
        }
        type = isReduced() ? "Normal" : "Reduced";

        return "Price: " + getPrice() + "\n"
                + "Validity: " + validity + "\n"
                + "Type: " + type;

    }
}
