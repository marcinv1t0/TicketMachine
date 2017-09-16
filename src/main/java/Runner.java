import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by m1per on 14.09.2017.
 */
public class Runner {

    public static void displayTickets(List<Ticket> tickets){
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(i + ". " + tickets.get(i).getShortInfo());
        }
    }

    private static void displayAvailableCurrency(List<BigDecimal> availableCurrency) {
        for (int i = 0; i < availableCurrency.size(); i++) {
            System.out.println(i + ". " + availableCurrency.get(i).toString());
        }
    }

    private static void displayReturnedCurrency(HashMap<BigDecimal, Integer> currencyToReturn) {
        currencyToReturn.forEach((k,v) -> System.out.println(k+" count: "+v));
    }

    public static void main (String args[]){
        TicketVendingMachine ticketMachine = new TicketVendingMachine();
        Scanner scanner = new Scanner(System.in);
        int menuInput;
        List<Ticket> availableTickets = new ArrayList<Ticket>();
        HashMap<BigDecimal, Integer> currencyToReturn = new HashMap<BigDecimal, Integer>();
        BigDecimal summedPrice, inputAmount;
        inputAmount = BigDecimal.ZERO;

        if(!ticketMachine.isChangeReturnPossible()){
            System.out.println("There may be a problem with change return due to\ntemporary unavailability of some coins");
        }

        System.out.println("1. Bilety czasowe \n2. Bilety jednorazowe");
        menuInput = Integer.parseInt(scanner.nextLine());

        switch (menuInput){
            case 1 :
                availableTickets = ticketMachine.getTimeTickets();
                break;
            case 2:
                availableTickets = ticketMachine.getSingleTickets();
                break;
        }
        displayTickets(availableTickets);
        menuInput = Integer.parseInt(scanner.nextLine());
        Ticket ticket = availableTickets.get(menuInput);

        System.out.println("1. Bilet normalny\n2. Bilet ulgowy");
        menuInput = Integer.parseInt(scanner.nextLine());
        if (menuInput == 2){
            ticketMachine.applyDiscount(ticket, new BigDecimal("0.35"));
        }
        System.out.println(ticket.getPrice());
        System.out.println("Liczba biletów: ");
        menuInput = Integer.parseInt(scanner.nextLine());
        summedPrice = ticketMachine.calculatePrice(ticket, menuInput);
        System.out.println(summedPrice);

        System.out.println("Zaplac");
        while (inputAmount.compareTo(summedPrice) == -1){
            System.out.println("Wprowadzono: " + inputAmount);
            displayAvailableCurrency(ticketMachine.getAvailableCurrency());
            menuInput = Integer.parseInt(scanner.nextLine());
            inputAmount = inputAmount.add(ticketMachine.getAvailableCurrency().get(menuInput));
        }

        currencyToReturn = ticketMachine.calculateChange(inputAmount, summedPrice);

        if (!ticketMachine.isChangeFromAmountPossible(summedPrice)){
            BigDecimal possibleReturn = ticketMachine.calculatePossibleChangeAmount(currencyToReturn);
            System.out.println("Currently it is impossible to return full change for\nselected ticket(s) " +
                    "closest possible return is " + possibleReturn);
            System.out.println("1. Continue 2. Cancel operation");
            menuInput = Integer.parseInt(scanner.nextLine());
            if (menuInput == 2){
                currencyToReturn = ticketMachine.calculateChange(inputAmount, BigDecimal.ZERO);
            }
        }
        displayReturnedCurrency(currencyToReturn);



    }



}
