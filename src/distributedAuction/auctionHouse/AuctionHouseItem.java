package auctionHouse;

import java.util.List;

public class AuctionHouseItem {

    private String itemName;
    private int itemId;
    private String itemDescription;
    private int minBid;
    private int currBid;
    private List<AuctionHouseItem> auctionItemsList;


    public int getItemId() {
            return itemId;
        }

    public int getMinBid() {
            return minBid;
        }

    public int getCurrBid() {
            return currBid;
        }

    public String getItemDescription() {
            return itemDescription;
        }


}
