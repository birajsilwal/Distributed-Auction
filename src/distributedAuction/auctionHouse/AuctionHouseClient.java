package distributedAuction.auctionHouse;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

//Auction house is client of Bank
public class AuctionHouseClient implements Runnable {

    private Socket socketClient;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
    private static final int BANK_PORT = 3000;
    private BufferedReader input;
    private PrintWriter output;

    AuctionHouseClient() {

    }


    @Override
    public void run() {

        try{
            System.out.println("Client started...");
            // establishes connection to the server port 4444
            socketClient = new Socket("localhost", BANK_PORT);
            System.out.println("Connected with the bank.");

            // takes data from socket client input stream
            input = new BufferedReader(
             new InputStreamReader(socketClient.getInputStream()));

            String str = input.readLine();

            if (str.equals("terminate")) {
                Terminate();
            }
            else {
                try {
                    processBankInput(str);
                    System.out.println();
                } catch (Exception e) {
                    System.out.println("Problem with taking Bank's input");
                    e.printStackTrace();
                }
            }
        }
        catch(Exception exception) {
            System.out.println("There is a problem with client.");
        }

    }

    public synchronized void Terminate() {
        System.out.println("Terminating the program...");
        System.out.println("Deregistering auction house with the bank.");
        try {
            socketClient.close();
        }
        catch (Exception e) {
            System.out.println("Error while terminating.");
            System.out.println("Hint: The program should not allow" +
                    " exit when there are still bids to be resolved.");
        }
    }

    public void processBankInput(String input) throws IOException {
        if (input != null) {
            String[] temp = input.split(" ");
            switch (temp[0]) {
                case "host":
                    output = new PrintWriter(socketClient.getOutputStream(), true);
                    output.println("Host IP Address is: " +  Inet4Address.getLocalHost().getHostAddress());
                case "port":
                    // output sends data to the server
                    // if false, output will not send data to server
                    output = new PrintWriter(socketClient.getOutputStream(), true);
//                    output.println("Host port is: " +  auctionHouseServer.getAuctionHouseServerPort());
                    output.println("Host port is: " + 9999);

            }
        }
    }


}
