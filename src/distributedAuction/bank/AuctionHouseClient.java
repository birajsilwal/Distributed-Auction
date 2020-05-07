package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AuctionHouseClient extends Bank implements Runnable{
    private String hostname;
    private int portNumber;
    private Account account;
    private BufferedReader in;
    private PrintWriter out;

    public AuctionHouseClient(String hostname, int portNumber, BufferedReader in, PrintWriter out, Account account, double startingBalance){
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.in = in;
        this.out = out;
        this.account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    public boolean matchHost(String hostname){
        return this.hostname.equals(hostname);
    }

    public boolean matchPortNumber(int portNumber){
        return this.portNumber == portNumber;
    }

    @Override
    public void run() {
        String inputLine = null;
        do {
            processAuctionHouseInput(inputLine, this);
            if (inputLine != null && inputLine.equals("closed")) {
                break;
            }
            try{
                inputLine = in.readLine();
            }catch(IOException ex) {
                inputLine = null;
            }
        }while(inputLine != null);
    }

    public Account getAccount() {
        return account;
    }

    public PrintWriter getOut() {
        return out;
    }
}
