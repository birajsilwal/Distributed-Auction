package distributedAuction.auctionHouse;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

//Auction house is client of Bank
public class AuctionHouseClient implements Runnable {

    private Socket socketClient;
    private PrintWriter output;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
    private static final int BANK_PORT = 4444;
    private BufferedReader input;

    AuctionHouseClient() {

    }

    @Override
    public void run() {

        try{
            System.out.println("Client started...");
            // establishes connection to the server port 4444
            socketClient = new Socket("localhost", BANK_PORT);
            System.out.println("Connected with the bank.");

            // input takes data from the user
            input = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.print("Enter your input: ");
            String str = input.readLine();

            // output sends data to the server
            // if false, output will not send data to server
            output = new PrintWriter(socketClient.getOutputStream(), true);
            output.println(str);

            socketClient.close();
        }
        catch(Exception exception) {
            System.out.println("There is a problem with client.");
        }

    }

    public synchronized void Terminate() {
        // terminate the program upon request by the user
    }


}
