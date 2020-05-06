package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Bank {
    private static int agents = 0;
    private static int houses = 0;
    private static int accountNumber = 1;

    private static Map<AgentClient, Account> agentAccountMap = new HashMap<>();
    private static Map<AuctionHouseClient, Account> auctionHouseAccountMap = new HashMap<>();

    private static Stack<Integer> housePortNumbers = new Stack<>();

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
                case "withdrawFunds":
                    client.getAccount().withdraw(Integer.parseInt(input[1]));
                    break;
                case "transfer":
                    int portNumber = Integer.parseInt(input[3]);
                    //System.out.println("Starting Balance: "+houseAccounts.get(portNumber).getAvailableBalance());
                    //withdraw the funds from the starting account
                    client.getAccount().withdraw(Integer.parseInt(input[1]));
                    //deposit funds into the ending account
                    //houseAccounts.get(portNumber).deposit(Integer.parseInt(input[1]));
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
                case "withdrawFunds":
                    client.getAccount().withdraw(Integer.parseInt(input[1]));
                    break;
                case "transfer":
                    int portNumber = Integer.parseInt(input[3]);
                    //System.out.println("Starting Balance: "+houseAccounts.get(portNumber).getAvailableBalance());
                    //withdraw the funds from the starting account
                    //account.withdraw(Integer.parseInt(input[1]));
                    //deposit funds into the ending account
                    //houseAccounts.get(portNumber).deposit(Integer.parseInt(input[1]));
                    break;
            }
        }
    }

    public static void main(String [] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
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
                if(!housePortNumbers.isEmpty()){
                    for(Integer number: housePortNumbers){
                        out.println("newAH: "+number);
                    }
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