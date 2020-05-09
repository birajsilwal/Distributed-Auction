/**CS 351 Charley Bickel Project 5 Distributed Auctions*/
package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * class for creating new AgentClients to communicate between agents and the
 * main bank. This class implements runnable to process messages independent of
 * the main execution thread to allow simultaneous communication between clients
 * and the bank server.
 */
public class AgentClient extends Bank implements Runnable{
    private Account account;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * creates a new instance of AgentClient to be run immediately after creation
     * @param in message inputStream to receive messages from the client
     * @param out message outputStream to send message to the client
     * @param account bank account for the connected agent
     * @param startingBalance initial deposit amount into the agents account
     */
    public AgentClient(BufferedReader in, PrintWriter out, Account account, double startingBalance){
        this.in = in;
        this.out = out;
        this.account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Method for process inputs from the agent to the bank and responding to
     * incoming messages. The do while loop blocks the thread until a new
     * message is received. The bank class then processes the new message and
     * sends a reply. This execution continues until the agent deregisters with
     * the bank.
     */
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

    /**
     * @return the agents bank account reference
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @return the message output to the agent
     */
    public PrintWriter getOut() {
        return out;
    }
}