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

    private static Map<AgentClient, Account> agentAccountMap = new HashMap<>();
    private static Map<AuctionHouseClient, Account> auctionHouseAccountMap = new HashMap<>();

    private Account getAuctionHouseAccount(String hostname, int portNumber){
        for(AuctionHouseClient client: auctionHouseAccountMap.keySet()){
            if(client.matchHost(hostname) && client.matchPortNumber(portNumber)){
                return client.getAccount();
            }
        }
        return null;
    }

    private void transferFunds(Account from, Account to){
        to.deposit(from.withdrawBlockedFunds());
    }

    protected void processAgentInput(String inputLine, AgentClient client){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
            switch (input[0]){
                case "deposit":
                    client.getAccount().deposit(Integer.parseInt(input[1]));
                    break;
                case "blockFunds":
                    client.getAccount().blockFunds(Integer.parseInt(input[1]));
                    break;
                case "transfer":
                    Account houseAccount = getAuctionHouseAccount(input[3], Integer.parseInt(input[4]));
                    if(houseAccount != null){
                        transferFunds(client.getAccount(), houseAccount);
                    }else{
                        System.out.println("Sorry that account doesn't exist");
                    }
                    break;
                case "deregister":
                    agentAccountMap.remove(client);
                    break;
            }
        }
    }



    protected void processAuctionHouseInput(String inputLine, AuctionHouseClient client){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
            switch (input[0]){
                case "deposit":
                    client.getAccount().deposit(Integer.parseInt(input[1]));
                    break;
                case "blockFunds":
                    client.getAccount().blockFunds(Integer.parseInt(input[1]));
                    break;
                case "deregister":
                    auctionHouseAccountMap.remove(client);
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
                agentAccountMap.put(client, account);
                for(AuctionHouseClient auctionHouseClient: auctionHouseAccountMap.keySet()){
                    out.println("newAH: "+auctionHouseClient.getHostAddress()+" "+auctionHouseClient.getPortNumber());
                }
            }
            if(input[0].equals("auctionhouse:")){
                System.out.println(inputLine+" Connected");
                Account account = new Account(accountNumber);
                AuctionHouseClient client = new AuctionHouseClient(input[1], Integer.parseInt(input[2]), bufferedReader, out, account, 0);
                out.println("accountNumber "+accountNumber);
                accountNumber++;
                auctionHouseAccountMap.put(client, account);
                for(AgentClient agentClient: agentAccountMap.keySet()){
                    agentClient.getOut().println("newAH: "+input[1]+" "+input[2]);
                }
            }
        }
    }
}