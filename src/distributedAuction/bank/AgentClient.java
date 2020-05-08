package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AgentClient extends Bank implements Runnable{
    private Account account;
    private BufferedReader in;
    private PrintWriter out;

    public AgentClient(BufferedReader in, PrintWriter out, Account account, double startingBalance){
        this.in = in;
        this.out = out;
        this.account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Agent thread is running");
        String inputLine = null;
        do {
            processAgentInput(inputLine, this);
            if (inputLine != null && inputLine.contains("deregister")) {
                System.out.println("Agent thread has been stopped");
                return;
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
