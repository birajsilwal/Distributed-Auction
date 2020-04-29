package client.auctionHouse;

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


    public void registerWithBank(int auctionHouseAccount, double auctionHouseBalance ) {
        auctionHouseBalance = 0;
    }

    public void updateBalance(int auctionHouseAccount, double amountFromBank) {
        auctionHouseBalance += amountFromBank;
    }



}
