package distributedAuction.bank;

public class Account{
    private int accountNum;
    private double availableBalance;
    private double blockedBalance;
    private double totalBalance;

    public void deposit(double amount){
        availableBalance = availableBalance + amount;
        updateTotalBalance();
        System.out.println("Deposited $"+amount+" new balance: "+availableBalance);
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    private boolean sufficientFunds(double amount){return availableBalance >= amount;}

    public double withdrawBlockedFunds(){
        double temp = blockedBalance;
        blockedBalance = 0;
        return temp;
    }

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

    public void unblockFunds(){
        availableBalance = availableBalance + blockedBalance;
        blockedBalance = 0;
    }

    private void updateTotalBalance(){
        totalBalance = availableBalance + blockedBalance;
    }

    public Account(int number){
        accountNum = number;
        availableBalance = 0;
        System.out.println("Bank account: "+number+" has been opened");
    }

    public int getAccountNum() {
        return accountNum;
    }
}
