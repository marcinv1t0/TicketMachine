import java.math.BigDecimal;

/**
 * Created by m1per on 13.09.2017.
 */
public class ShortTermTicket extends Ticket {
    private String validityTime;

    public ShortTermTicket() {
        super(new BigDecimal("0.0"));
    }

    public String getValidityTime() {
        return validityTime;
    }

    public ShortTermTicket(String validityTime, BigDecimal price) {
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

    public String getShortInfo() {
        return validityTime;
    }
}
