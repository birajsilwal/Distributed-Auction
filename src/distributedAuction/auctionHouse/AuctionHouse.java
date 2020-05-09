package distributedAuction.auctionHouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {

    private int auctionHouseAccount;
    private double auctionHouseBalance;
    private double amountFromBank;
    private int auctionHouseId;
    private String hostIpAddress;
    private int hostPortAddress;
    private AuctionHouseClient auctionHouseClient;
    private AuctionHouseServer auctionHouseServer;
    private List<String> auctionHouseItems;

    AuctionHouse() throws UnknownHostException {
        auctionHouseItems = new ArrayList<>();
        addItem();
        auctionHouseClient = new AuctionHouseClient();
        hostIpAddress = Inet4Address.getLocalHost().getHostAddress();
    }

    public int getAuctionHouseId() {
        return auctionHouseId;
    }


    // adding items into the list
    public void addItem() {
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("xbox", 100, "like new")));
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("violin", 2000, "antique")));
        auctionHouseItems.add(String.valueOf(new AuctionHouseItem("iphone X", 300, "like new")));

    }


    public void initializeAuctionHouse() throws UnknownHostException {
        auctionHouseClient.run();
        AuctionHouseServer auctionHouseServer = new AuctionHouseServer();
        auctionHouseServer.run();
    }


    public List<String> getAuctionHouseItems() {
        return auctionHouseItems;
    }



    public String getHostAddress() {
        return hostIpAddress;
    }

    public int getPortNumber() {
        return hostPortAddress;
    }


    public static void main(String[] args) throws UnknownHostException {
        AuctionHouse auctionHouse = new AuctionHouse();
        auctionHouse.initializeAuctionHouse();
    }


}
