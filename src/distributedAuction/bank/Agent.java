package distributedAuction.bank;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class Agent{
    private static Agent agent;
    private static Scanner scanner = new Scanner(System.in);
    private String name;
    private double balance;
    private int accountNum;

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private LinkedList<Integer> auctionHousePorts = new LinkedList<>();

    public Agent(String name, double balance) throws IOException {
        this.name = name;
        this.balance = balance;
        clientSocket = new Socket("10.20.10.167", 4444);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private void sendMessage(String Message){
        out.println(Message);
    }

    private void createAgent(){
        System.out.println("Please enter your name");
        agent.setName(scanner.nextLine());
        System.out.println("Please enter your starting balance");
        //agent.setBalance(scanner.nextDouble());
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