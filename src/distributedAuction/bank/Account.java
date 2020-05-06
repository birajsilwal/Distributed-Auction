package distributedAuction.bank;

public class Account{
    private int clientID;
    private double availableBalance;
    private double blockedBalance;

    public void deposit(double amount){
        availableBalance = availableBalance + amount;
        System.out.println("Deposited $"+amount+" new balance: "+availableBalance);
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    private boolean sufficientFunds(double amount){return availableBalance >= amount;}

    public void withdraw(double amount){
        if(sufficientFunds(amount)){
            availableBalance = availableBalance - amount;
            System.out.println("Withdrew "+amount+" from account. Balance=$"+availableBalance);
        }else{
            System.out.println("Insufficient funds, available balance remains unchanged");
        }
    }

    public void blockFunds(double amount){
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
