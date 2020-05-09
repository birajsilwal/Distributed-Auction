package distributedAuction.auctionHouse;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

//Auction house is client of Bank
public class AuctionHouseClient extends AuctionHouse implements Runnable {

    private Socket socketClient;
    private PrintWriter output;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
    private static final int BANK_PORT = 4444;
    private BufferedReader input;

    AuctionHouseClient() throws UnknownHostException {

    }

    @Override
    public void run() {

        try{
            System.out.println("Client started...");
            // establishes connection to the server port 4444
            socketClient = new Socket("localhost", BANK_PORT);
            System.out.println("Connected with the bank.");

//            // input takes data from the user
//            input = new BufferedReader(
//                    new InputStreamReader(System.in));
//            System.out.print("Enter your input: ");
//            String str = input.readLine();
//
//            // output sends data to the server
//            // if false, output will not send data to server
//            output = new PrintWriter(socketClient.getOutputStream(), true);
//            output.println(str);

            String inputFromBank = inputStream.readUTF();
            try {
                processBankInput(inputFromBank);
            }
            catch (Exception e) {
                System.out.println("Problem with taking Bank's input");
                e.printStackTrace();
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


}
