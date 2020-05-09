package distributedAuction.auctionHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

//Auction House is server for Agent
public class AuctionHouseServer extends AuctionHouse implements Runnable {

    private InputStreamReader inputReader;
    private BufferedReader input;
    private PrintWriter output;
    private ServerSocket serverSocket;
    private Socket socket;
    private int auctionHouseServerPort;

    AuctionHouseServer() throws UnknownHostException {
        super();
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

            if (str.equals("terminate")) {
                Terminate();
            }
            else {
                try {
                    processAgentInput(str);
                } catch (Exception e) {
                    System.out.println("Problem with taking Bank's input");
                    e.printStackTrace();
                }
            }
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

    public void processAgentInput(String input) throws IOException {
        if (input != null) {
            String[] temp = input.split(" ");
            switch (temp[0]) {
                case "item":
                    List<String> items = getAuctionHouseItems();

                    for (String item : items) {
                        output = new PrintWriter(socket.getOutputStream(), true);
                        output.println("Auction House Items: " + item);
                    }
            }
        }
    }

}
