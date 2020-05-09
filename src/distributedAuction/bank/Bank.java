package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Bank {
    private static int agents = 0;
    private static int houses = 0;
    private static int accountNumber = 1;

    private static Stack<AgentClient> connectedAgents = new Stack<>();
    private static Stack<AuctionHouseClient> connectedAuctionHouses = new Stack<>();
    private static Map<String, Account> houseAccountMap = new HashMap<>();

    private void transferFunds(Account from, Account to){
        to.deposit(from.withdrawBlockedFunds());
    }

    protected void processAgentInput(String inputLine, AgentClient client){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
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

    protected void processAuctionHouseInput(String inputLine, AuctionHouseClient client){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
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

    public static void main(String [] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Main Bank at address: "+Inet4Address.getLocalHost().getHostAddress());
        while (true){
            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String inputLine = bufferedReader.readLine();
            String[] input = inputLine.split(" ");
            if(input[0].equals("agent:")){
                System.out.println(inputLine+" Connected");
                Account account = new Account(accountNumber);
                AgentClient client = new AgentClient(bufferedReader, out, account, Double.parseDouble(input[3]));
                out.println("accountNumber "+accountNumber);
                accountNumber++;
                connectedAgents.add(client);
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
                for(AgentClient agentClient: connectedAgents){
                    agentClient.getOut().println("newAH: "+input[1]);
                }
            }
        }
    }
}