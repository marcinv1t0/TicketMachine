/**
 * Created by m1per on 14.09.2017.
 */
public class DiscountCalculator {

    public void applyDiscount(Ticket ticket, double discount){
        double price;

        price = ticket.getPrice();
        price *= 1 - discount;
        ticket.setPrice(price);
    }
}
