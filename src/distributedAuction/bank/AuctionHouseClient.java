/**CS 351L Charley Bickel Project 5 Distributed Auctions*/
package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class used for creating new AuctionHouse client objects for communication
 * between connected clients and the bank. This class implements runnable in
 * order to be able to continuously process incoming messages without
 * interrupting the main processing thread.
 */
public class AuctionHouseClient extends Bank implements Runnable{
    private String hostAddress;
    private Account account;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * creates a new auctionHouseClient object and starts it's thread to start
     * processes messages immediately after creation.
     * @param hostAddress the address of the auctionHouse serverSocket
     * @param in message input from the client
     * @param out message output to the client
     * @param account holds all auctionHouseClient funds
     * @param startingBalance money initially deposited to account normally 0
     */
    public AuctionHouseClient(String hostAddress, BufferedReader in, PrintWriter out, Account account, double startingBalance){
        this.hostAddress = hostAddress;
        this.in = in;
        this.out = out;
        this.account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Method for reading in new messages from the the client and processing.
     * The Do while loop is used to grab incoming strings from the inputStream
     * while also blocking the thread until a new message is received.
     * When a new message is received the message is sent to the bank for
     * processing.
     */
    @Override
    public void run() {
        System.out.println("Auction House thread is running");
        String inputLine = null;
        do {
            processAuctionHouseInput(inputLine, this);
            if (inputLine != null && inputLine.contains("deregister")) {
                System.out.println("Auction House thread has been stopped");
                return;
            }
            try{
                inputLine = in.readLine();
            }catch(IOException ex) {
                inputLine = null;
            }
        }while(inputLine != null);
    }

    /**
     * @return the auctionHouse's host address for it's client server
     */
    public String getHostAddress() {
        return hostAddress;
    }

    /**
     * @return the auctionHouse's bank account.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @return the auctionHouse's outputStream so the bank can send message to
     * the client.
     */
    public PrintWriter getOut() {
        return out;
    }
}
