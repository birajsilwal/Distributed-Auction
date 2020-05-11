package distributedAuction.auctionHouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {

    private AuctionHouseClient auctionHouseClient;
    private AuctionHouseServer auctionHouseServer;
    private List<String> auctionHouseItems;
    private final int auctionHouseServerPort = 9999;

    AuctionHouse() throws UnknownHostException {
        auctionHouseItems = new ArrayList<>();
        addItem();
    }

    // adding items into the list
    public void addItem() {
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("xbox", 100, "like new")));
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("violin", 2000, "antique")));
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("iphone X", 300, "like new")));
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("s20", 800, "like new")));

    }

    public void initializeAuctionHouse() throws UnknownHostException {
        auctionHouseClient = new AuctionHouseClient();
//        auctionHouseServer = new AuctionHouseServer(auctionHouseServerPort);
        auctionHouseClient.run();
//        auctionHouseServer.run();
    }


    public List<String> getAuctionHouseItems() {
        return auctionHouseItems;
    }

    public static void main(String[] args) throws UnknownHostException {
        AuctionHouse auctionHouse = new AuctionHouse();
        auctionHouse.initializeAuctionHouse();
    }


}
