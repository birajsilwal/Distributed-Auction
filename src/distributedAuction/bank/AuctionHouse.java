package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class AuctionHouse{
    private int balance;
    private int accountNum;
    private final ServerSocket serverSocket;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public AuctionHouse() throws IOException {
        balance = 0;
        serverSocket = new ServerSocket(4445);
        clientSocket = new Socket("10.20.10.242", 4444);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sendMessage("auctionhouse: "+Inet4Address.getLocalHost().getHostAddress()+" "+serverSocket.getLocalPort());
    }

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
