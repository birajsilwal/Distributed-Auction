package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AuctionHouseClient extends Bank implements Runnable{
    private String hostAddress;
    private int portNumber;
    private Account account;
    private BufferedReader in;
    private PrintWriter out;

    public AuctionHouseClient(String hostAddress, int portNumber, BufferedReader in, PrintWriter out, Account account, double startingBalance){
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
        this.in = in;
        this.out = out;
        this.account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    public boolean matchHost(String hostAddress){
        return this.hostAddress.equals(hostAddress);
    }

    public boolean matchPortNumber(int portNumber){
        return this.portNumber == portNumber;
    }

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

    public String getHostAddress() {
        return hostAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public Account getAccount() {
        return account;
    }

    public PrintWriter getOut() {
        return out;
    }
}
