package distributedAuction.bank;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;


public class Agent{
    private String name;
    private int balance;
    private int accountNum;

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private LinkedList<Integer> auctionHousePorts = new LinkedList<>();

    public Agent(String name, int balance) throws IOException {
        this.name = name;
        this.balance = balance;
        clientSocket = new Socket("localHost", 4444);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sendMessage("agent: "+name+" balance: "+balance);
    }

    private void processInput(String input){
        if(input != null){
            String[] inputs = input.split(" ");
            switch (inputs[0]){
                case "newAH:":
                    auctionHousePorts.add(Integer.parseInt(inputs[1]));
                    System.out.println("New Auction House Available");
                    System.out.println(auctionHousePorts.size()+" Auction Houses Available");
                    break;
                case "accountNumber":
                    accountNum = Integer.parseInt(inputs[1]);
                    System.out.println("Account number: "+accountNum);
                    break;
            }
        }
    }

    private void sendMessage(String Message){
        out.println(Message);
    }

    public static void main(String[] args) throws IOException{
        Agent agent = new Agent("charley",100000);
        String inputLine = null;
        do {
            agent.processInput(inputLine);
            if (inputLine != null && inputLine.equals("closed")) {
                break;
            }
            try{
                inputLine = agent.in.readLine();
            }catch(IOException ex) {
                inputLine = null;
            }
        }while(inputLine != null);
    }
}