package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class AuctionHouse implements Runnable{
    private final Random random = new Random();
    private final ServerSocket serverSocket;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public AuctionHouse() throws IOException {
        int portNumber = random.nextInt(10000);
        serverSocket = new ServerSocket(portNumber);
        clientSocket = new Socket("localHost", 4444);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println("auctionhouse: "+portNumber);
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) throws IOException{
        AuctionHouse auctionHouse = new AuctionHouse();
        AuctionHouse auctionHouse1 = new AuctionHouse();
    }
}
