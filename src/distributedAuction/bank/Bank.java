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

    private static Map<BankClient, Account> agentAccountMap = new HashMap<>();
    private static Map<BankClient, Account> auctionHouseAccountMap = new HashMap<>();
    private static Map<Integer, Account> housePortAccountMap = new HashMap<>();

    private static Stack<Integer> housePortNumbers = new Stack<>();

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
                BankClient client = new BankClient(ClientType.AGENT,
                        bufferedReader, out, account, Integer.parseInt(input[3]),
                        housePortAccountMap);
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
                BankClient client = new BankClient(ClientType.AUCTION_HOUSE, bufferedReader, out, account, 0);
                out.println("accountNumber "+accountNumber);
                accountNumber++;
                auctionHouseAccountMap.put(client, account);
                housePortNumbers.add(Integer.parseInt(input[1]));
                housePortAccountMap.put(Integer.parseInt(input[1]), account);
                for(BankClient bankClient: agentAccountMap.keySet()){
                    bankClient.getOut().println("newAH: "+input[1]);
                }
            }
        }
    }
}