package distributedAuction.auctionHouse;

import java.util.List;

/*AuctionHouseItem is an item object */
public class AuctionHouseItem {

    private String itemName;
    private int itemId;
    private String itemDescription;
    private List<AuctionHouseItem> auctionItemsList;
    private int minBid;
    private int agentId;

    AuctionHouseItem(String itemName, int minBid, String itemDescription) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.minBid = minBid;
        this.itemDescription = itemDescription;
        this.agentId = 0;
    }

    /**@return name of an item*/
    public String getItemName() {
        return itemName;
    }

    /**@return unique id of an item*/
    public int getItemId() {
            return itemId;
        }

    /**@return description of an item*/
    public String getItemDescription() {
            return itemDescription;
        }

    /**@param index is used to remove the sold item from the list of available items in AuctionHouse*/
    public void itemSold(int index) {
        auctionItemsList.remove(index);
    }

    public int getMinBid() {
        return minBid;
    }

    public void setMinBid(int amount, int agentId) {
        this.minBid = amount;
        this.agentId = agentId;
    }

    public String toString() {
        return "Item Name: " + itemName + ", minBid: " +
                minBid + ", item description: " + itemDescription;
    }

    /** @return id of an agent */
    public int getAgentId() {
        return agentId;
    }

}
