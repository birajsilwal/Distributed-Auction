/**CS 351 Charley Bickel Project 5 Distributed Auctions*/
package distributedAuction.bank;

/**
 * class for creating new bank account objects for all the connected clients
 * which access the bank. Each account is assigned a unique number and maintains
 * three double values for its total balance, available balance, and blocked funds.
 */
public class Account{
    private int accountNum;
    private double availableBalance;
    private double blockedBalance;
    private double totalBalance;

    /**
     * Deposits the specified amount of money into the bank account.
     * @param amount to be deposited.
     */
    public void deposit(double amount){
        availableBalance = availableBalance + amount;
        updateTotalBalance();
        System.out.println("Deposited $"+amount+" new balance: "+availableBalance);
    }

    /**
     * @return the accounts current available balance
     */
    public double getAvailableBalance() {
        return availableBalance;
    }

    /**
     *
     * @param amount to be check against the accounts available balance
     * @return true if the account has a sufficient available balance given the
     * specified amount.
     */
    private boolean sufficientFunds(double amount){return availableBalance >= amount;}

    /**
     * @return the funds previously blocked by an auctionHouse after an item
     * has been won to be transfer to the bank account of an auctionHouse.
     */
    public double withdrawBlockedFunds(){
        double temp = blockedBalance;
        blockedBalance = 0;
        return temp;
    }

    /**
     * Blocks available funds from being spent when an agent has committed to
     * a bid on an item.
     * @param amount the amount of funds to be blocked by the auctionHouse
     */
    public void blockFunds(double amount){
        if(sufficientFunds(amount)){
            blockedBalance = amount;
            availableBalance = availableBalance - amount;
            updateTotalBalance();
            System.out.println("$"+amount+" has been blocked");
        }else{
            System.out.println("Insufficient funds, available balance remains unchanged");
        }
    }

    /**
     * Used to unblock funds from an agents bank account when they have been
     * outbid on a given item.
     * @param amount to be returned to the agents available balance.
     */
    public void unblockFunds(double amount){
        if(amount <= blockedBalance){
            availableBalance = availableBalance + amount;
            blockedBalance = blockedBalance - amount;
        }else{
            System.out.println("Client tried to unblock more funds that were previously blocked. All funds have been unblocked");
            availableBalance = availableBalance + blockedBalance;
            blockedBalance = 0;
        }
        updateTotalBalance();
    }

    /**
     * used to update the accounts total balance after money has been blocked
     * unblocked withdrawn or deposited.
     */
    private void updateTotalBalance(){
        totalBalance = availableBalance + blockedBalance;
    }

    /**
     * Creates a new instance of the bank account object with the specified
     * account number.
     * @param number unique identifier for the account.
     */
    public Account(int number){
        accountNum = number;
        availableBalance = 0;
        System.out.println("Bank account: "+number+" has been opened");
    }
}
