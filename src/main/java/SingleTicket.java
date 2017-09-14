/**
 * Created by m1per on 14.09.2017.
 */
public class SingleTicket extends Ticket{
    private boolean isSpecial;

    public SingleTicket(){
        super(0.0);
        isSpecial = false;
    }

    public SingleTicket(double price, boolean isSpecial){
        super(price);
        this.isSpecial = isSpecial;
    }

    public String getPrintDetails() {
        String toPrint, type;

        type = isReduced() ? "Normal" : "Reduced";
        toPrint = "Price: " + getPrice()+ "\n"
                + "Type: " + type;
        if (isSpecial){
            toPrint += "\nSPECIAL TICKET";
        }
        return toPrint;
    }
}
