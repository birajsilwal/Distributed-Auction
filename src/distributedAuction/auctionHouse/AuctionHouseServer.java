package distributedAuction.auctionHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Auction House is server for Agent
public class AuctionHouseServer implements Runnable {

    private InputStreamReader inputReader;
    private BufferedReader input;
    private PrintWriter output;
    private ServerSocket serverSocket;
    private Socket socket;
    private int auctionHouseServerPort;

    AuctionHouseServer() {
        auctionHouseServerPort = 9999;
    }

    @Override
    public void run() {

        try {
            System.out.println("Waiting for client to connect...");
            serverSocket = new ServerSocket(auctionHouseServerPort);
            socket = serverSocket.accept();
            System.out.println("Client connection established.");

            // takes data from socket client input stream
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String str = input.readLine();
            output = new PrintWriter(socket.getOutputStream(), true);

            // sends str back to client. optional
            output.println("Server says: " + str);
            // prints here
            System.out.println("Client sent: " + str);

            serverSocket.close();
        }
        catch (Exception exception) {
            System.out.println("There is a problem with Auction House server.");
        }

    }

    public int getAuctionHouseServerPort() {
        return serverSocket.getLocalPort();
    }


    public synchronized void Terminate() {
        System.out.println("Terminating the program...");
        System.out.println("Deregistering auction house with the bank.");
        try {
            socket.close();
        }
        catch (Exception e) {
            System.out.println("Error while terminating.");
            System.out.println("Hint: The program should not allow" +
                    " exit when there are still bids to be resolved.");
        }
    }

}
