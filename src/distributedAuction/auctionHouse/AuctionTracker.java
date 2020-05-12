package distributedAuction.auctionHouse;

import java.util.Timer;
import java.util.TimerTask;

/*AuctionTrack keeps track of auction[s]*/
public class  AuctionTracker extends AuctionHouseServer implements Runnable {

    // currBid is the updated bid of an item
    private int currBid;
    // time to finish taking bidding
    private int timeToOvertakeBid;

    private Boolean biddingStatusIsBidding;
    private Boolean biddingStatusItemIsSold;
    private int hostAddress;

    AuctionTracker() {
        // need to update minBid and currBid
        currBid = 0;
        timeToOvertakeBid = 30; // 30 sec
        biddingStatusIsBidding = false;
        biddingStatusItemIsSold = false;
//        this.hostAddress = hostAddress;
    }

    /**@return current bid status*/
    public int getCurrBid() {
        return currBid;
    }

    /*resets time to overtake bid into 30 seconds*/
    public void resetTimeToOvertakeBid() {
        this.timeToOvertakeBid = 30;
    }

    /* this method sets timer i.e. 30 seconds countdown.
    * user must make bid within this time otherwise item will be sold*/
    public void setTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                    timeToOvertakeBid--;
                    bidSuccessfulMessage();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    /*status of an item sold will be true when bidding is successful */
    public void bidSuccessfulMessage() {
        if (timeToOvertakeBid == 0) {
            biddingStatusItemIsSold = true;
            System.out.println("Congratulations!!! Bid Successful.");
        }
    }

    /**@return bidding status, whether item is bidding or not*/
    public Boolean getBiddingStatusIsBidding() {
        return biddingStatusIsBidding;
    }

    /**@return bidding status, whether item is sold or not */
    public Boolean getBiddingStatusItemIsSold() {
        return biddingStatusItemIsSold;
    }


    @Override
    public void run() {

    }
}
