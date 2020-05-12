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
    private List<AuctionHouseItem> auctionHouseItems;
    private final int auctionHouseServerPort = 9999;
    private Socket socketBank;

    AuctionHouse() throws UnknownHostException {
        auctionHouseItems = new ArrayList<>();
        addItem();
    }

    // adding items into the list
    public void addItem() {
        auctionHouseItems.add(new AuctionHouseItem("xbox", 100, "like new"));
        auctionHouseItems.add(new AuctionHouseItem("violin", 2000, "antique"));
        auctionHouseItems.add(new AuctionHouseItem("iphone X", 300, "like new"));
        auctionHouseItems.add(new AuctionHouseItem("s20", 800, "like new"));

    }

    public void initializeAuctionHouse() throws UnknownHostException {
        auctionHouseClient = new AuctionHouseClient(socketBank);
        auctionHouseServer = new AuctionHouseServer(auctionHouseServerPort, auctionHouseItems, socketBank);
        auctionHouseClient.run();
//        auctionHouseServer.run();
    }


    public List<AuctionHouseItem> getAuctionHouseItems() {
        return auctionHouseItems;
    }

    public static void main(String[] args) throws UnknownHostException {
        AuctionHouse auctionHouse = new AuctionHouse();
        auctionHouse.initializeAuctionHouse();
    }


}
