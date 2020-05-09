package distributedAuction.auctionHouse;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

/*AuctionHouseClient is client of Bank, auction house is connected to bank's
* server via socket port 3000*/
public class AuctionHouseClient implements Runnable {

    // socketClient is socket of AuctionHouse
    private Socket socketClient;
    // BANK_PORT is port of the bank
    private static final int BANK_PORT = 3000;
    // input is used to take input from bank
    private BufferedReader input;
    // output is used to send data back to the bank's server
    private PrintWriter output;

    AuctionHouseClient() {

    }

    /*Socket connection is happening here as well as bank's input is parsed here*/
    @Override
    public void run() {
        try{
            System.out.println("Client started...");
            // establishes connection to the bank's server port
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

    /*this method terminated the socket connection with bank upon the request of user*/
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

    /**@param input is passed as Bank's input and prints out stuff */
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
