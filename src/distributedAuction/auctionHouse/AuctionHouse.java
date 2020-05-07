package distributedAuction.auctionHouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private int auctionHouseAccount;
    private double auctionHouseBalance;
    private double amountFromBank;
    private String agentName;
    private int auctionHouseId;
    private String hostIpAddress;
    private String hostPortAddress;
    private AuctionHouseClient auctionHouseClient;
    private AuctionHouseServer auctionHouseServer;
    private List<AuctionHouseItem> auctionHouseItems;

    AuctionHouse() throws UnknownHostException {
        auctionHouseItems = new ArrayList<>();
        auctionHouseClient = new AuctionHouseClient();
        auctionHouseServer = new AuctionHouseServer();
        hostIpAddress = ("Host IP Address is: " + Inet4Address.getLocalHost().getHostAddress());
        hostPortAddress = ("Host Port Address is: " + auctionHouseServer.getAuctionHouseServerPort());
    }


    public void registerWithBank(int auctionHouseAccount, double auctionHouseBalance) {
        auctionHouseBalance = 0;
    }

    public void deregisterWithBank() {
        //when agent terminates, it deregister with the bank
    }

    public void updateBalance(int auctionHouseAccount, double amountFromBank) {
        auctionHouseBalance += amountFromBank;
    }


    public void checkBalance() {
        System.out.println("Your balance is: " + auctionHouseBalance);
    }


    public int getAuctionHouseId() {
        return auctionHouseId;
    }


    // menu/ list of commands to display for users


    // adding items into the list
    public void addItem() {
        auctionHouseItems.add(new AuctionHouseItem("xbox", 100, "like new"));
        auctionHouseItems.add(new AuctionHouseItem("violin", 2000, "antique"));
        auctionHouseItems.add(new AuctionHouseItem("iphone X", 300, "like new"));

    }

    public void initializeAuctionHouse() {
        // initialization of auction house
    }

}
