import java.math.BigDecimal;
import java.util.*;

/**
 * Created by m1per on 14.09.2017.
 */
public class Client {
    private final static int DEFAULT_INVALID_INPUT = -1;
    private static TicketVendingMachine ticketMachine = new TicketVendingMachine();
    private static Scanner scanner = new Scanner(System.in);
    private static List<Ticket> availableTickets = new ArrayList<>();

    public static void displayTickets(List<Ticket> tickets) {
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(i + 1 + ". " + tickets.get(i).getShortInfo() + " : "
                    + tickets.get(i).getPrice());
        }
        System.out.println(tickets.size() + 1 + ". Cancel");
    }

    public static void displayReducedTickets(List<Ticket> tickets) {
        BigDecimal discount = new BigDecimal(ticketMachine.getDiscountValue());
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(i + 1 + ". " + tickets.get(i).getShortInfo() + " : "
                    + ticketMachine.getReducedPrice(tickets.get(i), discount));
        }
        System.out.println(tickets.size() + 1 + ". Cancel");
    }

    private static void displayAvailableCurrency(List<BigDecimal> availableCurrency) {
        for (int i = 0; i < availableCurrency.size(); i++) {
            System.out.println(i + 1 + ". " + availableCurrency.get(i).toString());
        }
        System.out.println(availableCurrency.size() + 1 + ". Cancel");
    }

    private static void displayReturnedCurrency(TreeMap<BigDecimal, Integer> coinsToReturn) {
        if (coinsToReturn.isEmpty()) {
            System.out.println("-----");
        } else {
            for (Map.Entry<BigDecimal, Integer> curr : coinsToReturn.entrySet()) {
                if (curr.getValue() > 0)
                    System.out.println(curr.getKey() + " count: " + curr.getValue());
            }
        }
    }

    public static void main(String args[]) {
        int ticketCount;
        BigDecimal summedPrice, inputAmount, ticketPrice, discount;
        TreeMap<BigDecimal, Integer> currencyToReturn;
        boolean closeProgram, reduced;
        closeProgram = reduced = false;
        discount = new BigDecimal(ticketMachine.getDiscountValue());

        while (!closeProgram) {
            System.out.println("\n\n");
            System.out.println("WELCOME!\nPlease navigate by entering integer values assigned to menu options.");
            System.out.println("\n\n");

            /////////////////////////////SHORT TERM TICKETS / SINGLE TICKETS SELECTION /////////////////////////////////

            System.out.println("1. SHORT-TERM TICKETS \n2. SINGLE TICKETS");
            int menuInput = DEFAULT_INVALID_INPUT;
            while (menuInput < 1 || menuInput > 2) {
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()) {
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > 2) {
                        System.out.print("\nIncorrect number!\n");
                    }
                }
            }

            switch (menuInput) {
                case 1:
                    availableTickets = ticketMachine.getTimeTickets();
                    break;
                case 2:
                    availableTickets = ticketMachine.getSingleTickets();
                    break;
            }

            ////////////////////////////////////NORMAL/REDUCED TICKET SELECTION/////////////////////////////////////////
            System.out.println("\n\n");
            System.out.println("1. NORMAL TICKET\n2. REDUCED TICKET\n3. Cancel\n");
            menuInput = DEFAULT_INVALID_INPUT;
            while (menuInput < 1 || menuInput > 3) {
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()) {
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > 3) {
                        System.out.print("\nIncorrect number!\n");
                    }
                }
            }
            switch (menuInput) {
                case 1:
                    displayTickets(availableTickets);
                    reduced = false;
                    break;
                case 2:
                    displayReducedTickets(availableTickets);
                    reduced = true;
                    break;
                case 3:
                    continue;
            }

            /////////////////////////////////////SPECIFIC TICKET SELECTION//////////////////////////////////////////////

            System.out.println("\n\n");
            menuInput = DEFAULT_INVALID_INPUT;
            while (menuInput < 1 || menuInput > availableTickets.size() + 1) {
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()) {
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 1 || menuInput > availableTickets.size() + 1) {
                        System.out.print("\nIncorrect number!\n");
                    }
                }
            }
            if (menuInput == (availableTickets.size() + 1)) {
                continue;
            }
            Ticket ticket = availableTickets.get(menuInput - 1);
            ticketPrice = reduced ? ticketMachine.getReducedPrice(ticket, discount) : ticket.getPrice();


            // //////////////////////////////////////TICKET COUNT SELECTION/////////////////////////////////////////////

            System.out.println("\n\n");
            System.out.println("TICKET COUNT");
            menuInput = DEFAULT_INVALID_INPUT;

            while (menuInput < 0 || !ticketMachine.isPrintPossible(menuInput)) {
                System.out.print("Enter count: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()) {
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 0) {
                        System.out.print("\nNumber cannot be negative\n");
                    }
                    if (!ticketMachine.isPrintPossible(menuInput)) {
                        System.out.println("\nCurrently it is impossible to print selected number of tickets.\n" +
                                "Please, select smaller number." + " Maximum number: " + ticketMachine.getInkLevel() + "\n");
                    }
                }
            }

            /////////////////////////////////////////////////PAYMENT////////////////////////////////////////////////////

            System.out.println("\n\n");
            ticketCount = menuInput;
            summedPrice = ticketMachine.calculatePrice(ticketPrice, menuInput);
            System.out.println("Amount to pay: " + summedPrice);
            inputAmount = BigDecimal.ZERO;
            System.out.println("VALID COINS:");
            displayAvailableCurrency(ticketMachine.getAvailableCoinInput());
            System.out.println("\n");

            while (inputAmount.compareTo(summedPrice) == -1) {
                System.out.println("AMOUNT ENTERED: " + inputAmount);
                menuInput = DEFAULT_INVALID_INPUT;
                while (menuInput < 1 || menuInput > ticketMachine.getAvailableCoinInput().size() + 1) {
                    System.out.print("Select option: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("\nPlease, enter integer value!\nSelect option: ");
                        scanner.next();
                    }
                    if (scanner.hasNextInt()) {
                        menuInput = scanner.nextInt();
                        scanner.nextLine();
                        if (menuInput < 1 || menuInput > ticketMachine.getAvailableCoinInput().size() + 1) {
                            System.out.print("\nIncorrect number!\n");
                        }
                    }
                }
                if (menuInput == ticketMachine.getAvailableCoinInput().size() + 1) {
                    break;
                }

                BigDecimal selectedCoin = ticketMachine.getAvailableCoinInput().get(menuInput - 1);
                inputAmount = inputAmount.add(selectedCoin);
                ticketMachine.putCoins(selectedCoin, 1);
            }


            ///////////////////////////////////////////CHANGE RETURN///////////////////////////////////////////////////

            System.out.println("\n\n");
            currencyToReturn = ticketMachine.calculateChange(inputAmount, summedPrice);
            if (!ticketMachine.isChangeFromAmountPossible(inputAmount.subtract(summedPrice))) {
                BigDecimal possibleReturn = ticketMachine.calculatePossibleChangeAmount(currencyToReturn);
                System.out.println("Currently it is impossible to return full change for\nselected ticket(s) " +
                        "closest possible return is " + possibleReturn);
                System.out.println("1. Continue 2. Cancel operation");
                menuInput = Integer.parseInt(scanner.nextLine());
                if (menuInput >= 2) {
                    currencyToReturn = ticketMachine.calculateChange(inputAmount, BigDecimal.ZERO);
                }
            }
            ticketMachine.returnChange(currencyToReturn);
            System.out.println("RETURNED COINS:");
            displayReturnedCurrency(currencyToReturn);

            ////////////////////////////////////////////////PRINT///////////////////////////////////////////////////////

            for (int i = 0; i < ticketCount; i++) {
                ticketMachine.print(ticket);
            }

            ////////////////////////////////////////////////FINISH//////////////////////////////////////////////////////

            System.out.println("\n");
            menuInput = DEFAULT_INVALID_INPUT;
            System.out.println("CLOSE PROGRAM? 1/0");
            while (menuInput < 0 || menuInput > 1) {
                System.out.print("Select option: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("\nPlease, enter integer value!\nSelect option: ");
                    scanner.next();
                }
                if (scanner.hasNextInt()) {
                    menuInput = scanner.nextInt();
                    scanner.nextLine();
                    if (menuInput < 0 || menuInput > 1) {
                        System.out.print("\nIncorrect number!\n");
                    }
                }
            }



            switch (menuInput) {
                case 1:
                    closeProgram = true;
                    break;
                case 2:
                    closeProgram = false;
            }
        }

    }

}




