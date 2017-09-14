/**
 * Created by m1per on 13.09.2017.
 */
public abstract class Ticket {
    private double price;
    private boolean isReduced;

    public Ticket(double price){
        this.price = price;
        isReduced = false;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
}
