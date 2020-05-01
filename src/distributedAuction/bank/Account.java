package distributedAuction.bank;

public class Account{
    private int clientID;
    private int availableBalance;
    private int blockedBalance;

    public void deposit(int amount){
        availableBalance = availableBalance + amount;
        System.out.println("Deposited $"+amount+" new balance: "+availableBalance);
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    private boolean sufficientFunds(int amount){return availableBalance >= amount;}

    public void withdraw(int amount){
        if(sufficientFunds(amount)){
            availableBalance = availableBalance - amount;
        }else{
            System.out.println("Insufficient funds, available balance remains unchanged");
        }
    }

    public void blockFunds(int amount){
        if(sufficientFunds(amount)){
            blockedBalance = amount;
            availableBalance = availableBalance - amount;
            System.out.println("$"+amount+" has been blocked");
        }else{
            System.out.println("Insufficient funds, available balance remains unchanged");
        }
    }

    public Account(int number){
        clientID = number;
        availableBalance = 0;
        System.out.println("Bank account: "+number+" has been opened");
    }
}
