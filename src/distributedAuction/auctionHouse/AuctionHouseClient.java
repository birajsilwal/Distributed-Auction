package distributedAuction.auctionHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class AuctionHouseClient extends AuctionHouseServer implements Runnable{
    private int id;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socketAgent;

    /**
     * creates a new instance of AgentClient to be run immediately after creation
     * @param in message inputStream to receive messages from the client
     * @param out message outputStream to send message to the client
     * @param id unique identifier for the connected agent
     */
    public AuctionHouseClient(BufferedReader in, PrintWriter out, int id, Socket socketAgent){
        this.id = id;
        this.in = in;
        this.out = out;
        this.socketAgent = socketAgent;
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
        String inputLine = null;
        do {
            try {
                processAgentInput(inputLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
     * @return the message output to the agent
     */
    public PrintWriter getOut() {
        return out;
    }

    public int getId() {
        return id;
    }

    public Socket getSocketAgent() {
        return socketAgent;
    }
}
