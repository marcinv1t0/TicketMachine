import org.junit.Assert
/**
 * Created by m1per on 18.09.2017.
 */
class TicketVendingMachineTest extends GroovyTestCase {

    void testGetSingleTickets() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        List<Ticket> tickets = ticketVendingMachine.getSingleTickets()
        Assert.assertNotNull(tickets)
        for (Ticket ticket : tickets){
            Assert.assertNotNull(ticket)
        }
    }

    void testGetTimeTickets() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        List<Ticket> tickets = ticketVendingMachine.getTimeTickets()
        Assert.assertNotNull(tickets)
        for (Ticket ticket : tickets){
            Assert.assertNotNull(ticket)
        }
    }

    void testGetAllTickets() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        List<Ticket> tickets = ticketVendingMachine.getAllTickets()
        Assert.assertNotNull(tickets)
        for (Ticket ticket : tickets){
            Assert.assertNotNull(ticket)
        }
    }

    void testPrint() {
        TicketPrinter printer = new TicketPrinter()
        int startingLevel = printer.getInkLevel()
        Ticket ticket = new SingleTicket()
        printer.print(ticket)
        Assert.assertEquals(printer.getInkLevel(), startingLevel - 1)
    }

    void testCalculateChange() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        TreeMap<BigDecimal, Integer> expectedChange = new TreeMap<>()
        CSVWriter writer = new CSVWriter()

        expectedChange.put(new BigDecimal("5.0"), 10)
        expectedChange.put(new BigDecimal("2.0"), 10)
        expectedChange.put(new BigDecimal("1.0"), 10)
        expectedChange.put(new BigDecimal("0.5"), 10)
        expectedChange.put(new BigDecimal("0.2"), 10)
        expectedChange.put(new BigDecimal("0.1"), 10)

        writer.writeMap("./settings/coins.csv", expectedChange)
        expectedChange.clear()

        expectedChange.put(new BigDecimal("5.0"), 0)
        expectedChange.put(new BigDecimal("2.0"), 1)
        expectedChange.put(new BigDecimal("1.0"), 1)
        expectedChange.put(new BigDecimal("0.5"), 0)
        expectedChange.put(new BigDecimal("0.2"), 0)
        expectedChange.put(new BigDecimal("0.1"), 0)

        Assert.assertEquals(expectedChange, ticketVendingMachine.calculateChange(new BigDecimal("5.0"), new BigDecimal("2.0")))

        expectedChange.clear()
        expectedChange.put(new BigDecimal("5.0"), 0)
        expectedChange.put(new BigDecimal("2.0"), 1)
        expectedChange.put(new BigDecimal("1.0"), 0)
        expectedChange.put(new BigDecimal("0.5"), 1)
        expectedChange.put(new BigDecimal("0.2"), 1)
        expectedChange.put(new BigDecimal("0.1"), 0)

        Assert.assertEquals(expectedChange, ticketVendingMachine.calculateChange(new BigDecimal("5.0"), new BigDecimal("2.26")))
    }

    void testCalculatePrice() {
        TicketVendingMachine ticketMachine = new TicketVendingMachine()

        Assert.assertEquals(ticketMachine.calculatePrice(new BigDecimal("1.0"), 1), 1.00, 0)
        Assert.assertEquals(ticketMachine.calculatePrice(new BigDecimal("1.0"), 2), 2.00, 0)
        Assert.assertEquals(ticketMachine.calculatePrice(new BigDecimal("1.0"), -1), -1.00, 0)
        Assert.assertEquals(ticketMachine.calculatePrice(new BigDecimal("5.62"), 10), 56.20, 0)
        Assert.assertEquals(ticketMachine.calculatePrice(new BigDecimal("1.96"), 4), 7.84, 0)
        Assert.assertEquals(ticketMachine.calculatePrice(new BigDecimal("1.0"), 0), 0.00, 0)
        Assert.assertEquals(ticketMachine.calculatePrice(BigDecimal.ZERO, 4), 0.00, 0)
    }

    void testGetReducedPrice() {
        TicketVendingMachine ticketMachine = new TicketVendingMachine()
        Ticket ticket = new SingleTicket(false, new BigDecimal(2.0))
        BigDecimal reducedPrice, discount
        discount = new BigDecimal("0.25")

        reducedPrice = ticketMachine.getReducedPrice(ticket, discount)
        Assert.assertEquals(new BigDecimal("1.5"), reducedPrice, 0.00)

        ticket.setPrice(new BigDecimal("1.26"))
        reducedPrice = ticketMachine.getReducedPrice(ticket, discount)
        Assert.assertEquals(new BigDecimal("0.95"), reducedPrice, 0.00)

        ticket.setPrice(new BigDecimal("9.06"))
        reducedPrice = ticketMachine.getReducedPrice(ticket, discount)
        Assert.assertEquals(new BigDecimal("6.80"), reducedPrice, 0.00)

        ticket.setPrice(new BigDecimal("-0.89"))
        reducedPrice = ticketMachine.getReducedPrice(ticket, discount)
        Assert.assertEquals(new BigDecimal("-0.67"), reducedPrice, 0.00)
    }

    void testGetAvailableCoinInput() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        List<BigDecimal> coins = new ArrayList<>()
        TreeMap<BigDecimal, Integer> coinsWithCount = new TreeMap<>(Collections.reverseOrder())
        CSVWriter writer = new CSVWriter()

        coinsWithCount.put(new BigDecimal("5.0"), 10)
        coinsWithCount.put(new BigDecimal("2.0"), 10)
        coinsWithCount.put(new BigDecimal("1.0"), 10)
        coinsWithCount.put(new BigDecimal("0.5"), 10)
        coinsWithCount.put(new BigDecimal("0.2"), 10)
        coinsWithCount.put(new BigDecimal("0.1"), 10)

        writer.writeMap("./settings/coins.csv", coinsWithCount)

        Assert.assertNotNull(ticketVendingMachine.getAvailableCoinInput())
        coins.addAll(coinsWithCount.keySet())
        Assert.assertEquals(coins, ticketVendingMachine.getAvailableCoinInput())
    }

    void testIsChangeFromAmountPossible() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        TreeMap<BigDecimal, Integer> coinsWithCount = new TreeMap<>(Collections.reverseOrder())
        CSVWriter writer = new CSVWriter()

        coinsWithCount.put(new BigDecimal("5.0"), 10)
        coinsWithCount.put(new BigDecimal("2.0"), 10)
        coinsWithCount.put(new BigDecimal("1.0"), 10)
        coinsWithCount.put(new BigDecimal("0.5"), 10)
        coinsWithCount.put(new BigDecimal("0.2"), 10)
        coinsWithCount.put(new BigDecimal("0.1"), 0)

        writer.writeMap("./settings/coins.csv", coinsWithCount)

        Assert.assertTrue(ticketVendingMachine.isChangeFromAmountPossible(new BigDecimal("2.0")))
        Assert.assertFalse(ticketVendingMachine.isChangeFromAmountPossible(new BigDecimal("4000.0")))
        Assert.assertFalse(ticketVendingMachine.isChangeFromAmountPossible(new BigDecimal("1.96")))
        Assert.assertFalse(ticketVendingMachine.isChangeFromAmountPossible(new BigDecimal("1.80")))
        Assert.assertTrue(ticketVendingMachine.isChangeFromAmountPossible(BigDecimal.ZERO))
    }

    void testCalculatePossibleChangeAmount(){
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        TreeMap<BigDecimal, Integer> change = new TreeMap<>()

        change.put(new BigDecimal("5.0"), 0)
        change.put(new BigDecimal("2.0"), 0)
        change.put(new BigDecimal("1.0"), 1)
        change.put(new BigDecimal("0.5"), 1)
        change.put(new BigDecimal("0.2"), 1)
        change.put(new BigDecimal("0.1"), 1)

        Assert.assertEquals(new BigDecimal("1.8"), ticketVendingMachine.calculatePossibleChangeAmount(change), 0.0)

        change.clear()
        change.put(new BigDecimal("5.0"), 1)
        change.put(new BigDecimal("2.0"), 1)
        change.put(new BigDecimal("1.0"), 1)
        change.put(new BigDecimal("0.5"), 1)
        change.put(new BigDecimal("0.2"), 1)
        change.put(new BigDecimal("0.1"), 1)

        Assert.assertEquals(new BigDecimal("8.8"), ticketVendingMachine.calculatePossibleChangeAmount(change), 0.0)

        change.clear()
        change.put(new BigDecimal("5.0"), 0)
        change.put(new BigDecimal("2.0"), 0)
        change.put(new BigDecimal("1.0"), 0)
        change.put(new BigDecimal("0.5"), 0)
        change.put(new BigDecimal("0.2"), 1)
        change.put(new BigDecimal("0.1"), 1)

        Assert.assertEquals(new BigDecimal("0.3"), ticketVendingMachine.calculatePossibleChangeAmount(change), 0.0)

        change.clear()
        change.put(new BigDecimal("5.0"), 0)
        change.put(new BigDecimal("2.0"), 0)
        change.put(new BigDecimal("1.0"), 0)
        change.put(new BigDecimal("0.5"), 0)
        change.put(new BigDecimal("0.2"), 0)
        change.put(new BigDecimal("0.1"), 0)

        Assert.assertEquals(BigDecimal.ZERO , ticketVendingMachine.calculatePossibleChangeAmount(change), 0.0)


    }

    void testGetCoinsWithCount() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        TreeMap<BigDecimal, Integer> coins = new TreeMap<>(Collections.reverseOrder())
        CSVWriter writer = new CSVWriter()

        coins.put(new BigDecimal("5.0"), 1)
        coins.put(new BigDecimal("2.0"), 1)
        coins.put(new BigDecimal("1.0"), 1)
        coins.put(new BigDecimal("0.5"), 1)
        coins.put(new BigDecimal("0.2"), 1)
        coins.put(new BigDecimal("0.1"), 1)
        writer.writeMap("./settings/coins.csv", coins)

        Assert.assertEquals(coins, ticketVendingMachine.getCoinsWithCount())

        coins.clear()
        coins.put(new BigDecimal("5.0"), 0)
        coins.put(new BigDecimal("2.0"), 1)
        coins.put(new BigDecimal("1.0"), 2)
        coins.put(new BigDecimal("0.5"), 45)
        coins.put(new BigDecimal("0.2"), 12)
        coins.put(new BigDecimal("0.1"), 0)
        writer.writeMap("./settings/coins.csv", coins)

        Assert.assertEquals(coins, ticketVendingMachine.getCoinsWithCount())
    }

    void testPutCoins() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        TreeMap<BigDecimal, Integer> coins = new TreeMap<>(Collections.reverseOrder())
        CSVWriter writer = new CSVWriter()

        coins.put(new BigDecimal("5.0"), 0)
        coins.put(new BigDecimal("2.0"), 0)
        coins.put(new BigDecimal("1.0"), 0)
        coins.put(new BigDecimal("0.5"), 0)
        coins.put(new BigDecimal("0.2"), 0)
        coins.put(new BigDecimal("0.1"), 0)
        writer.writeMap("./settings/coins.csv", coins)

        ticketVendingMachine.putCoins(new BigDecimal("2.0"), 4)
        ticketVendingMachine.putCoins(new BigDecimal("0.1"), 2)

        coins.put(new BigDecimal("5.0"), 0)
        coins.put(new BigDecimal("2.0"), 4)
        coins.put(new BigDecimal("1.0"), 0)
        coins.put(new BigDecimal("0.5"), 0)
        coins.put(new BigDecimal("0.2"), 0)
        coins.put(new BigDecimal("0.1"), 2)

        Assert.assertEquals(coins, ticketVendingMachine.getCoinsWithCount())
    }

    void testWithdrawCoins() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()
        TreeMap<BigDecimal, Integer> coins = new TreeMap<>(Collections.reverseOrder())
        CSVWriter writer = new CSVWriter()

        coins.put(new BigDecimal("5.0"), 10)
        coins.put(new BigDecimal("2.0"), 10)
        coins.put(new BigDecimal("1.0"), 10)
        coins.put(new BigDecimal("0.5"), 10)
        coins.put(new BigDecimal("0.2"), 10)
        coins.put(new BigDecimal("0.1"), 10)
        writer.writeMap("./settings/coins.csv", coins)

        ticketVendingMachine.withdrawCoins(new BigDecimal("2.0"), 4)
        ticketVendingMachine.withdrawCoins(new BigDecimal("0.1"), 2)

        coins.put(new BigDecimal("5.0"), 10)
        coins.put(new BigDecimal("2.0"), 6)
        coins.put(new BigDecimal("1.0"), 10)
        coins.put(new BigDecimal("0.5"), 10)
        coins.put(new BigDecimal("0.2"), 10)
        coins.put(new BigDecimal("0.1"), 8)

        Assert.assertEquals(coins, ticketVendingMachine.getCoinsWithCount())
    }

    void testIsPrintPossible() {
        TicketVendingMachine ticketVendingMachine = new TicketVendingMachine()

        ticketVendingMachine.getTicketPrinter().setInkLevel(0)
        Assert.assertFalse(ticketVendingMachine.isPrintPossible(1))
        ticketVendingMachine.getTicketPrinter().setInkLevel(2)
        Assert.assertFalse(ticketVendingMachine.isPrintPossible(3))
        ticketVendingMachine.getTicketPrinter().setInkLevel(10)
        Assert.assertTrue(ticketVendingMachine.isPrintPossible(3))




    }

}
