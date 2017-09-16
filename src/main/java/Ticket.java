import java.math.BigDecimal;

/**
 * Created by m1per on 13.09.2017.
 */
public abstract class Ticket {
    private BigDecimal price;
    private boolean isReduced;

    public Ticket(BigDecimal price){
        this.price = price;
        isReduced = false;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isReduced() {
        return isReduced;
    }

    public void setReduced(boolean reduced) {
        isReduced = reduced;
    }

    @Override
    public String toString() {
        return getPrintDetails();
    }

    public abstract String getPrintDetails();

    public abstract String getShortInfo();
}
