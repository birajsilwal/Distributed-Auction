package Bank;

import java.net.Socket;

public class Account implements Runnable {
    private int clientID;
    private int balance;
    private ClientType clientType;
    private Socket clientSocket;

    public void deposit(int amount){
        balance = balance + amount;
    }

    private boolean sufficientFunds(int amount){return balance >= amount;}

    public void withdraw(int amount){
        if(sufficientFunds(amount)){
            balance = balance - amount;
        }else{
            System.out.println("Insufficient funds, balance remains unchanged");
        }
    }

    public Account(int id, ClientType type, Socket socket){
        clientID = id;
        clientType = type;
        clientSocket = socket;
        balance = 0;
        System.out.println("New Bank account opened by "+type+" with id: "+id);
    }

    @Override
    public void run() {

    }
}
