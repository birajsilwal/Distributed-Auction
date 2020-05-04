package distributedAuction.auctionHouse;

import java.util.List;

public class AuctionHouseItem {

    private String itemName;
    private int itemId;
    private String itemDescription;
    private List<AuctionHouseItem> auctionItemsList;

    AuctionHouseItem(String itemName, int itemId) {
        this.itemName = itemName;
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemId() {
            return itemId;
        }

    public String getItemDescription() {
            return itemDescription;
        }

        /**@param index is used to remove the sold item from the list of available items in AuctionHouse*/
    public void itemSold(int index) {
        auctionItemsList.remove(index);
    }

    public List<AuctionHouseItem> getAuctionItemsList() {
        return auctionItemsList;
    }

    public void getOfferedItems() {

    }


}
