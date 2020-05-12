package distributedAuction.auctionHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*AuctionHouseServer is server for Agent. Port 9999 is used for all
* auction house server but host address is different */
public class AuctionHouseServer extends AuctionHouse implements Runnable {

    // input is used to get input from agent
    private BufferedReader input;
    // bankInput is to get input from bank
    private BufferedReader bankInput;
    // output is used to send data back to the agent
    private PrintWriter outputAgent;
    // printWriterBank is to send data to bank server
    private PrintWriter printWriterBank;
    // serverSocket is required for server
    private ServerSocket serverSocket;
    // socket is used for communication with agent
    private Socket socket;
    // socket is used for communication with bank
    private Socket socketBank;
    // port for AuctionHouse Server
    private int auctionHouseServerPort;

    private AuctionTracker auctionTracker;
    // list of an item available in auction house
    private List<AuctionHouseItem> auctionHouseItemList;
    // amount bidded by agent
    private int bidAmount;
    // index of item selected by agent
    private int itemIndex;
    // name of an item
    private String itemName;
    // id of an agent
    private int agentId;
    private Map<AuctionHouseClient, Integer> clientIDMap;
    private Boolean timerRunning;


    AuctionHouseServer(int auctionHouseServerPort, List<AuctionHouseItem>
            auctionHouseItemList, Socket socketBank, BufferedReader bankInput) {
        super();
        this.auctionHouseServerPort = auctionHouseServerPort;
        auctionTracker = new AuctionTracker();
        this.bankInput = bankInput;
        this.auctionHouseItemList = auctionHouseItemList;
        itemName = "";
        clientIDMap = new HashMap<>();

        agentId = 1;
        timerRunning = true;
        this.socketBank = socketBank;
        Thread thread = new Thread(this);
        thread.start();
    }

    public AuctionHouseServer() {
    }

    @Override
    public void run(){
        try {
            System.out.println("AH server: Waiting for client to connect...");
            serverSocket = new ServerSocket(auctionHouseServerPort);
            socket = serverSocket.accept();
            System.out.println("Client connection established.");

            // takes data from socket client input stream
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            outputAgent = new PrintWriter(socket.getOutputStream());
            AuctionHouseClient client = new AuctionHouseClient(input, outputAgent, agentId, socket);
            clientIDMap.put(client, agentId);
            agentId++;

            String str = input.readLine();

            try {
                processAgentInput(str);
            } catch (Exception e) {
                System.out.println("Problem with taking Agent's input");
                e.printStackTrace();
            }

        }
        catch (Exception exception) {
            System.out.println("There is a problem with Auction House server.");
        }

    }

    /*Method for terminating server and closing socket upon request of agent*/
    public synchronized void Terminate() {
        System.out.println("Terminating the program...");
        System.out.println("Deregistering auction house with the bank.");
        try {
            socket.close();
        }
        catch (Exception e) {
            System.out.println("Error while terminating.");
            System.out.println("Hint: The program should not allow" +
                    " exit when there are still bids to be resolved.");
        }
    }

    /**@param input is used as input from agent and used for further processing*/
    public void processAgentInput(String input) throws IOException {
        if (input != null) {
            String[] temp = input.split(" ");
            switch (temp[0]) {

                case "item":
                    List<AuctionHouseItem> items = getAuctionHouseItems();

                    for (AuctionHouseItem item : items) {
                        outputAgent = new PrintWriter(socket.getOutputStream(), true);
                        outputAgent.println("Auction House Items: " + item);
                    }

                case "terminate":
                    if (auctionTracker.getBiddingStatusIsBidding()) {
                        System.out.println("Can't terminate when bidding is going.");
                    }
                    else {
                        Terminate();
                    }

                case "bid":
                    // item selected by agent
                    itemIndex = Integer.parseInt(temp[1]);
                    // amount that agent bid on an item. hardcoded 500 just to test
                    bidAmount = Integer.parseInt(temp[2]);

                    try {
                        auctionTracker.setTimer();
                        while (timerRunning = true) {
                            AuctionHouseItem auctionHouseItem = auctionHouseItemList.get(itemIndex);
                            agentId = auctionHouseItem.getAgentId();
                            AuctionHouseClient auctionHouseClient = null;

                            // if its different agent than the above 1
                            if (auctionHouseItem.getAgentId() != 1) {
                                for (Map.Entry<AuctionHouseClient, Integer> entry : clientIDMap.entrySet()) {
                                    if (entry.getKey().getId() == agentId) {
                                        auctionHouseClient = entry.getKey();
                                    }
                                    Socket socketAgent = auctionHouseClient.getSocketAgent();
                                    outputAgent = new PrintWriter(socketAgent.getOutputStream());
                                    outputAgent.println("Your bid is outbidded.");
                                    outputAgent.flush();
                                    auctionTracker.resetTimeToOvertakeBid();
                                }
                            } else {
                                itemName = auctionHouseItem.getItemName();
                                auctionHouseItem.setMinBid(bidAmount, clientIDMap.get(agentId));

                                printWriterBank = new PrintWriter(socketBank.getOutputStream(), true);
                                printWriterBank.println("Unblock " + bidAmount);

                                outputAgent.println("Bid successful!!!");
                                timerRunning = false;
                            }
                        }
                    }
                    catch (Exception e) {
                        System.out.println("There is a problem with bidding. Bidding unsuccessful.");
                        e.printStackTrace();
                    }
            }
            processAgentInput(input);
        }
    }

}
