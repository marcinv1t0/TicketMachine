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
        if (coinsToReturn.isEmpty()){
            System.out.println("-----");
        }else{
            for (Map.Entry<BigDecimal, Integer> curr :coinsToReturn.entrySet()) {
                if ( curr.getValue() > 0 )
                    System.out.println(curr.getKey() + " count: " + curr.getValue());
            }
        }



    }

    public static void main (String args[]){
        final int DEFAULT_INVALID_INPUT = -1;
        TicketVendingMachine ticketMachine = new TicketVendingMachine();
        Scanner scanner = new Scanner(System.in);
        int menuInput;
        List<Ticket> availableTickets = new ArrayList<>();
        TreeMap<BigDecimal, Integer> currencyToReturn = new TreeMap<BigDecimal, Integer>();
        BigDecimal summedPrice, inputAmount, ticketPrice;


        while(true){
            System.out.println("\n\n");
            System.out.println("WELCOME!\nPlease navigate by entering integer values assigned to menu options.");
            System.out.println("\n\n");

            /////////////////////////////SHORT TERM TICKETS / SINGLE TICKETS SELECTION /////////////////////////////////

            System.out.println("1. SHORT-TERM TICKETS \n2. SINGLE TICKETS");
            menuInput = DEFAULT_INVALID_INPUT;
            while (menuInput < 1 || menuInput > 2){
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > 2){ System.out.print("\nIncorrect number!\n"); }
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

            /////////////////////////////////////SPECIFIC TICKET SELECTION//////////////////////////////////////////////

            System.out.println("\n\n");
            displayTickets(availableTickets);
            System.out.println();
            menuInput = DEFAULT_INVALID_INPUT;
            while (menuInput < 1 || menuInput > availableTickets.size()+1){
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > availableTickets.size()+1){ System.out.print("\nIncorrect number!\n"); }
                }
            }
            if (menuInput == availableTickets.size()+1) { continue; }
            Ticket ticket = availableTickets.get(menuInput-1);
            ticketPrice = ticket.getPrice();

            ////////////////////////////////////NORMAL/REDUCED TICKET SELECTION/////////////////////////////////////////
            System.out.println("\n\n");
            System.out.println("1. NORMAL TICKET\n2. REDUCED TICKET\n3. Cancel\n");
            menuInput = DEFAULT_INVALID_INPUT;
            while (menuInput < 1 || menuInput > 3){
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > 3){ System.out.print("\nIncorrect number!\n"); }
                }
            }
            switch (menuInput){
                case 2:
                    ticketPrice = ticketMachine.getReducedPrice(ticket, new BigDecimal(ticketMachine.getDiscountValue()));
                    break;
                case 3:
                    continue;
            }

            // //////////////////////////////////////TICKET COUNT SELECTION/////////////////////////////////////////////

            System.out.println("\n\n");
            System.out.println("TICKET COUNT");
            menuInput = DEFAULT_INVALID_INPUT;

            while (menuInput < 0 || !ticketMachine.isPrintPossible(menuInput)){
                System.out.print("Enter count: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()){
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 0){ System.out.print("\nNumber cannot be negative\n"); }
                    if (!ticketMachine.isPrintPossible(menuInput)){
                        System.out.println("\nCurrently it is impossible to print selected number of tickets.\n" +
                                "Please, select smaller number." + " Maximum number: " + ticketMachine.getInkLevel() + "\n");
                    }
                }
            }

            /////////////////////////////////////////////////PAYMENT////////////////////////////////////////////////////

            System.out.println("\n\n");
            summedPrice = ticketMachine.calculatePrice(ticketPrice, menuInput);
            System.out.println("Amount to pay: " + summedPrice);
            inputAmount = BigDecimal.ZERO;
            System.out.println("VALID COINS:");
            displayAvailableCurrency(ticketMachine.getAvailableCoinInput());
            System.out.println("\n");

            while (inputAmount.compareTo(summedPrice) == -1){
                System.out.println("AMOUNT ENTERED: " + inputAmount);
                menuInput = -1;
                while (menuInput < 1 || menuInput > ticketMachine.getAvailableCoinInput().size()+1){
                    System.out.print("Select option: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("\nPlease, enter integer value!\nSelect option: ");
                        scanner.next();
                    }
                    if (scanner.hasNextInt()){
                        menuInput = scanner.nextInt();
                        scanner.nextLine();
                        if (menuInput < 1 || menuInput > ticketMachine.getAvailableCoinInput().size()+1){ System.out.print("\nIncorrect number!\n"); }
                    }
                }
                if (menuInput == ticketMachine.getAvailableCoinInput().size()+1) { break; }

                BigDecimal selectedCoin = ticketMachine.getAvailableCoinInput().get(menuInput-1);
                inputAmount = inputAmount.add(selectedCoin);
                ticketMachine.putCoins(selectedCoin , 1);
            }

            ///////////////////////////////////////////CHANGE RETURN///////////////////////////////////////////////////

            System.out.println("\n\n");
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
            System.out.println("RETURNED COINS:");
            displayReturnedCurrency(currencyToReturn);
        }



    }



}
