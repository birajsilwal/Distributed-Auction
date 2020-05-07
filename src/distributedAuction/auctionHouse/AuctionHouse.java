package distributedAuction.auctionHouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class AuctionHouse {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private int auctionHouseAccount;
    private double auctionHouseBalance;
    private double amountFromBank;
    private String agentName;
    private int auctionHouseId;

    AuctionHouse() {
        // creating auction house
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

    public void createAuctionHouse() {
        // upon creation, register with bank, open account with zero balance
    }

    public int getAuctionHouseId() {
        return auctionHouseId;
    }


    // menu/ list of commands to display for users


    // adding items into the list

    public void initializeAuctionHouse() {
        // initialization of auction house
    }

}
