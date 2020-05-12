package distributedAuction.auctionHouse;

import java.io.BufferedReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {

    private AHBankClient AHBankClient;
    private AuctionHouseServer auctionHouseServer;
    private List<AuctionHouseItem> auctionHouseItems;
    private final int auctionHouseServerPort = 9999;
    private Socket socketBank;
    private AuctionTracker auctionTracker;
    private BufferedReader bankInput;

    AuctionHouse() {
        auctionHouseItems = new ArrayList<>();
        addItem();
        socketBank = new Socket();
        auctionTracker = new AuctionTracker();
    }

    // adding items into the list
    public void addItem() {
        auctionHouseItems.add(new AuctionHouseItem("xbox", 100, "like new"));
        auctionHouseItems.add(new AuctionHouseItem("violin", 2000, "antique"));
        auctionHouseItems.add(new AuctionHouseItem("iphone X", 300, "like new"));
        auctionHouseItems.add(new AuctionHouseItem("s20", 800, "like new"));

    }

    public void initializeAuctionHouse() throws UnknownHostException {
        AHBankClient = new AHBankClient(socketBank, bankInput);
        auctionHouseServer = new AuctionHouseServer(auctionHouseServerPort, auctionHouseItems, socketBank, bankInput);
        AHBankClient.run();
        auctionHouseServer.run();
    }


    public List<AuctionHouseItem> getAuctionHouseItems() {
        return auctionHouseItems;
    }

    public static void main(String[] args) throws UnknownHostException {
        AuctionHouse auctionHouse = new AuctionHouse();
        auctionHouse.initializeAuctionHouse();
    }
}
