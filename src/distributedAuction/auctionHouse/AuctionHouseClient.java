package auctionHouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class AuctionHouseClient implements Runnable {
    Socket socket;
    PrintWriter writer;

    AuctionHouseClient() throws IOException {
        // trying to establish a connection with server into the port 8080
        socket = new Socket("localhost", 4444);
        writer = new PrintWriter(socket.getOutputStream());
        writer.println("AuctionHouse Client");
        writer.flush();
    }



    public static void main(String[] args) {


    }

    @Override
    public void run() {

    }
}
