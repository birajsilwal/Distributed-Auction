/**CS 351 Charley Bickel Project 5 Distributed Auctions*/
package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Class used for creation of the bank server for the entire program.
 * Assigns account numbers sequentially as new clients connect.
 * Maintains two static collections tracking actively connected clients
 * while also maintaining a map for fast lookup of accounts based on corresponding
 * IP addresses.
 */
public class Bank {
    private static int accountNumber = 1;

    private static Stack<AgentClient> connectedAgents = new Stack<>();
    private static Stack<AuctionHouseClient> connectedAuctionHouses = new Stack<>();
    private static Map<String, Account> houseAccountMap = new HashMap<>();

    /**
     * Method for transfering funds between bank accounts
     * @param from the account that is providing the funds
     * @param to the account that is receiving the funds
     */
    private void transferFunds(Account from, Account to){
        to.deposit(from.withdrawBlockedFunds());
    }

    /**
     * Method for processing messages sent to the bank from the agent class.
     * Instructs the bank to transfer, block, unblock, deposit or deregister.
     * @param message sent from the client via PrintWriter
     * @param client reference to client object which sent the message.
     */
    protected void processAgentInput(String message, AgentClient client){
        if(message != null){
            String[] input = message.split(" ");
            switch (input[0]){
                case "deposit":
                    double depositAmount = Double.parseDouble(input[1]);
                    client.getAccount().deposit(depositAmount);
                    client.getOut().println("Deposited $"+depositAmount+" new balance $"+client.getAccount().getAvailableBalance());
                    break;
                case "blockFunds":
                    double blockAmount = Double.parseDouble(input[1]);
                    client.getAccount().blockFunds(blockAmount);
                    client.getOut().println("Blocked $"+blockAmount);
                    break;
                case "unblockFunds":
                    double unblockAmount = Double.parseDouble(input[1]);
                    client.getAccount().unblockFunds(unblockAmount);
                    client.getOut().println("Unblocked funds, new available balance: $"+client.getAccount().getAvailableBalance());
                    break;
                case "transfer":
                    Account houseAccount = houseAccountMap.get(input[2]);
                    if(houseAccount != null){
                        transferFunds(client.getAccount(), houseAccount);
                    }else{
                        System.out.println("Sorry that account doesn't exist");
                    }
                    break;
                case "deregister":
                    connectedAgents.remove(client);
                    break;
            }
        }
    }

    /**
     * Method for processing messages sent to the bank from the auction house
     * class. Can instruct the bank to deposit, block and unblock funds and
     * deregister when transactions are all complete.
     * @param message string instructions sent from the client class via PrintWriter
     * @param client reference to the client object which provided the instructions
     */
    protected void processAuctionHouseInput(String message, AuctionHouseClient client){
        if(message != null){
            String[] input = message.split(" ");
            switch (input[0]){
                case "deposit":
                    double depositAmount = Double.parseDouble(input[1]);
                    client.getAccount().deposit(depositAmount);
                    client.getOut().println("Deposited $"+depositAmount+" new balance $"+client.getAccount().getAvailableBalance());
                    break;
                case "blockFunds":
                    double blockAmount = Double.parseDouble(input[1]);
                    client.getAccount().blockFunds(blockAmount);
                    client.getOut().println("Blocked $"+blockAmount);
                    break;
                case "unblockFunds":
                    double unblockAmount = Double.parseDouble(input[1]);
                    client.getAccount().unblockFunds(unblockAmount);
                    client.getOut().println("Unblocked funds, new available balance: $"+client.getAccount().getAvailableBalance());
                    break;
                case "deregister":
                    connectedAuctionHouses.remove(client);
                    houseAccountMap.remove(client.getHostAddress());
                    break;
            }
        }
    }

    /**
     * processes new client connections to the server and assigns them based on
     * class specification. This method sets up bank accounts and client threads
     * to handle concurrent communication between the various object and the bank
     * @param args
     * @throws IOException
     */
    public static void main(String [] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Main Bank at address: "+Inet4Address.getLocalHost().getHostAddress());
        while (true){
            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            //Reads the initial message to determine which type of client to create.
            String inputLine = bufferedReader.readLine();
            String[] input = inputLine.split(" ");
            if(input[0].equals("agent:")){
                System.out.println(inputLine+" Connected");
                Account account = new Account(accountNumber);
                //Establishes a new client with the given parameters
                AgentClient client = new AgentClient(bufferedReader, out, account, Double.parseDouble(input[3]));
                out.println("accountNumber "+accountNumber);
                accountNumber++;
                connectedAgents.add(client);
                //Informs connected client of all available auctionHouses
                for(AuctionHouseClient auctionHouseClient: connectedAuctionHouses){
                    out.println("newAH: "+auctionHouseClient.getHostAddress());
                }
            }
            if(input[0].equals("auctionhouse:")){
                System.out.println(inputLine+" Connected");
                Account account = new Account(accountNumber);
                houseAccountMap.put(input[1], account);
                AuctionHouseClient client = new AuctionHouseClient(input[1], bufferedReader, out, account, 0);
                out.println("accountNumber "+accountNumber);
                accountNumber++;
                connectedAuctionHouses.add(client);
                //Informs previously connected clients of the newly available
                //Auction house
                for(AgentClient agentClient: connectedAgents){
                    agentClient.getOut().println("newAH: "+input[1]);
                }
            }
        }
    }
}