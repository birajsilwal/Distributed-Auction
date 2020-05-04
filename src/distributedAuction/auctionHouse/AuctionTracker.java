package distributedAuction.auctionHouse;

import java.util.Timer;
import java.util.TimerTask;

/*AuctionTrack keeps track of auction*/
public class  AuctionTracker implements Runnable {
    private int minBid;
    private int currBid;
    private int timeToOvertakeBid;

    AuctionTracker() {
        // need to update minBid and currBid
        minBid = 0;
        currBid = 0;
        timeToOvertakeBid = 30; // 30 sec
    }


    public int getMinBid() {
        return minBid;
    }

    public int getCurrBid() {
        return currBid;
    }

    public void resetTimeToOvertakeBid() {
        this.timeToOvertakeBid = 30;
    }

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

    public void bidSuccessfulMessage() {
        if (timeToOvertakeBid == 0) {
            System.out.println("Congratulations!!! Bid Successful.");
        }
    }


    @Override
    public void run() {

    }
}
