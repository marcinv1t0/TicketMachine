import java.math.BigDecimal;

/**
 * Created by m1per on 14.09.2017.
 */
public class SingleTicket extends Ticket {
    private boolean isSpecial;
    private String discount;

    public SingleTicket() {
        super(new BigDecimal("0.0"));
        isSpecial = false;
    }

    public SingleTicket(boolean isSpecial, BigDecimal price) {
        super(price);
        this.isSpecial = isSpecial;
    }

    public String getPrintDetails() {
        String toPrint;

        discount = isReduced() ? "Normal" : "Reduced";
        toPrint = "Price: " + getPrice() + "\n"
                + "Type: " + discount;
        if (isSpecial) {
            toPrint += "\nSPECIAL TICKET";
        }
        return toPrint;
    }

    public String getShortInfo() {
        return isSpecial ? "SPECIAL TCIKET" : "NORMAL TICKET";
    }
}
