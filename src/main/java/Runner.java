import java.math.BigDecimal;
import java.util.*;

/**
 * Created by m1per on 14.09.2017.
 */
public class Runner {

    public static void displayTickets(List<Ticket> tickets){
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(i+1 + ". " + tickets.get(i).getShortInfo());
        }
        System.out.println(tickets.size() + 1 + ". Anuluj");
    }

    private static void displayAvailableCurrency(List<BigDecimal> availableCurrency) {
        for (int i = 0; i < availableCurrency.size(); i++) {
            System.out.println(i+1 + ". " + availableCurrency.get(i).toString());
        }
        System.out.println(availableCurrency.size() + 1 + ". Anuluj");

    }

    private static void displayReturnedCurrency(TreeMap<BigDecimal, Integer> coinsToReturn) {
        for (Map.Entry<BigDecimal, Integer> curr :coinsToReturn.entrySet()) {
            if ( curr.getValue() > 0 )
            System.out.println(curr.getKey() + " count: " + curr.getValue());
        }


    }

    public static void main (String args[]){
        TicketVendingMachine ticketMachine = new TicketVendingMachine();
        Scanner scanner = new Scanner(System.in);
        int menuInput;
        List<Ticket> availableTickets = new ArrayList<>();
        TreeMap<BigDecimal, Integer> currencyToReturn = new TreeMap<BigDecimal, Integer>();
        BigDecimal summedPrice, inputAmount;

        /*if(!ticketMachine.isChangeReturnPossible()){
            System.out.println("There may be a problem with change return due to\ntemporary unavailability of some coins");
        }*/

        // SINGLE/TIME SELECTION

        while(true){
            System.out.println("1. Bilety czasowe \n2. Bilety jednorazowe");
            menuInput = -1;
            while (menuInput < 1 || menuInput > 2){
                while (!scanner.hasNextInt()) {
                    System.out.println("Wybrano niepoprawną pozycję");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > 2){ System.out.println("Wybrano niepoprawną pozycję"); }
                }
            }

            switch (menuInput){
                case 1 :
                    availableTickets = ticketMachine.getTimeTickets();
                    break;
                case 2:
                    availableTickets = ticketMachine.getSingleTickets();
                    break;
            }
            displayTickets(availableTickets);

            menuInput = -1;
            while (menuInput < 1 || menuInput > availableTickets.size()+1){
                while (!scanner.hasNextInt()) {
                    System.out.println("Wybrano niepoprawną pozycję");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > availableTickets.size()+1){ System.out.println("Wybrano niepoprawną pozycję"); }
                }
            }
            if (menuInput == availableTickets.size()+1) { continue; }
            Ticket ticket = availableTickets.get(menuInput-1);

            // NORMAL/REDUCED SELECTION

            System.out.println("1. Bilet normalny\n2. Bilet ulgowy\n3. Anuluj");
            menuInput = -1;
            while (menuInput < 1 || menuInput > 3){
                while (!scanner.hasNextInt()) {
                    System.out.println("Wybrano niepoprawną pozycję");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > 3){ System.out.println("Wybrano niepoprawną pozycję"); }
                }
            }
            switch (menuInput){
                case 2:
                    ticketMachine.applyDiscount(ticket, new BigDecimal("0.35"));
                    break;
                case 3:
                    continue;
            }
            System.out.println(ticket.getPrice());

            // TICKET COUNT SELECTION

            System.out.println("Liczba biletow: ");
            menuInput = -1;

            while (menuInput < 0){
                while (!scanner.hasNextInt()) {
                    System.out.println("Prosze wprowadzic LICZBE calkowita");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 0){ System.out.println("Liczba biletow nie moze byc ujemna"); }
                }
            }

            summedPrice = ticketMachine.calculatePrice(ticket, menuInput);
            System.out.println(summedPrice);

            // PAYMENT
            inputAmount = BigDecimal.ZERO;
            System.out.println("Zaplac");
            while (inputAmount.compareTo(summedPrice) == -1){
                System.out.println("Wprowadzono: " + inputAmount);
                displayAvailableCurrency(ticketMachine.getAvailableCoinInput());
                menuInput = -1;
                while (menuInput < 1 || menuInput > ticketMachine.getAvailableCoinInput().size()+1){
                    while (!scanner.hasNextInt()) {
                        System.out.println("Wybrano niepoprawną pozycję");
                        scanner.next();
                    }
                    if (scanner.hasNextInt()){
                        menuInput = scanner.nextInt();
                        scanner.nextLine();
                        if (menuInput < 1 || menuInput > ticketMachine.getAvailableCoinInput().size()+1){ System.out.println("Wybrano niepoprawną pozycję"); }
                    }
                }
                if (menuInput == ticketMachine.getAvailableCoinInput().size()+1) { break; }

                BigDecimal selectedCoin = ticketMachine.getAvailableCoinInput().get(menuInput-1);
                inputAmount = inputAmount.add(selectedCoin);
                ticketMachine.putCoins(selectedCoin , 1);
            }

            currencyToReturn = ticketMachine.calculateChange(inputAmount, summedPrice);

            if (!ticketMachine.isChangeFromAmountPossible(inputAmount.subtract(summedPrice))){
                BigDecimal possibleReturn = ticketMachine.calculatePossibleChangeAmount(currencyToReturn);
                System.out.println("Currently it is impossible to return full change for\nselected ticket(s) " +
                        "closest possible return is " + possibleReturn);
                System.out.println("1. Continue 2. Cancel operation");
                menuInput = Integer.parseInt(scanner.nextLine());
                if (menuInput == 2){
                    currencyToReturn = ticketMachine.calculateChange(inputAmount, BigDecimal.ZERO);
                }
            }
            ticketMachine.returnChange(currencyToReturn);
            displayReturnedCurrency(currencyToReturn);
        }



    }



}
