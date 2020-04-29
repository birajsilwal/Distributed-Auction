package distributedAuction.auctionHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class AuctionHouseServer implements Runnable {

    private InputStreamReader inputReader;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private ServerSocket serverSocket;

    AuctionHouseServer() throws IOException {
        serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        inputReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputReader);
        printWriter.println(bufferedReader);
    }

    public static void main(String[] args) {



    }

    @Override
    public void run() {

    }
}
