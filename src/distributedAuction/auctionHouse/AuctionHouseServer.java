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
    private final static int AGENT_PORT = 1111;

    AuctionHouseServer() {

    }

    @Override
    public void run() {

        try {
            System.out.println("Waiting for client to connect...");
            serverSocket = new ServerSocket(AGENT_PORT);
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
}
