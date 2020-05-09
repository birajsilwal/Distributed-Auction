package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class AuctionHouse{
    private int balance;
    private int accountNum;
    private final ServerSocket serverSocket;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //Creates a new auctionHouse on the client IP address with the standard port
    public AuctionHouse() throws IOException {
        balance = 0;
        //Sets up the server for the auction house
        serverSocket = new ServerSocket(4445);
        //Connects as a client to the bank
        clientSocket = new Socket("10.20.10.167", 4444);
        //sets up readers and writers for the input output streams
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Tells the bank what address it's on so that agents may connect
        sendMessage("auctionhouse: "+Inet4Address.getLocalHost().getHostAddress());
    }

    //Handles messages sent to the auctionHouse from the bank.
    private void processInput(String inputLine){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
            switch (input[0]){
                case "accountNumber":
                    accountNum = Integer.parseInt(input[1]);
                    System.out.println("Account number: "+accountNum);
            }
        }
    }

    //Uses the printwriter to send string messages to the bank.
    private void sendMessage(String Message){out.println(Message);}

    public static void main(String[] args) throws IOException{
        AuctionHouse auctionHouse = new AuctionHouse();
        String inputLine = null;
        do {
            auctionHouse.processInput(inputLine);
            if (inputLine != null && inputLine.equals("closed")) {
                break;
            }
            try{
                inputLine = auctionHouse.in.readLine();
            }catch(IOException ex) {
                inputLine = null;
            }
        }while(inputLine != null);
    }
}