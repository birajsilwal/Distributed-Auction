package Bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class Bank {
    private static int agents = 0;
    private static int houses = 0;
    private static Stack<AuctionHouse> houseStack = new Stack<>();
    private static Stack<Account> houseAccounts = new Stack<>();
    private static Stack<Account> agentAccounts = new Stack<>();
    private static Stack<Socket> agentSockets = new Stack<>();
    private static Stack<Socket> houseSockets = new Stack<>();
    private static Stack<Integer> housePortNumbers = new Stack<>();

    public static void main(String [] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        while (true){
            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String input = bufferedReader.readLine();
            String[] inputs = input.split(" ");
            if(inputs[0].equals("agent")){
                System.out.println(input+" Connected");
                Account account = new Account(Integer.parseInt(inputs[1]), ClientType.AGENT, clientSocket);
                agentAccounts.add(account);
                agentSockets.add(clientSocket);
                if(!housePortNumbers.isEmpty()){
                    for(Integer number: housePortNumbers){
                        out.println("newAuctionHouse: "+number);
                    }
                }
                System.out.println(agentSockets.size()+" agents connected");
            }
            if(inputs[0].equals("auctionhouse:")){
                System.out.println(input+" Connected");
                Account account = new Account(Integer.parseInt(inputs[1]), ClientType.AUCTION_HOUSE, clientSocket);
                houseAccounts.add(account);
                houseSockets.add(clientSocket);
                housePortNumbers.add(Integer.parseInt(inputs[1]));
                for(Socket client: agentSockets){
                    PrintWriter tempOut = new PrintWriter(client.getOutputStream(), true);
                    tempOut.println("newAuctionHouse: "+inputs[1]);
                }
                System.out.println(houseSockets.size()+" houses connected");
            }
        }
    }
}