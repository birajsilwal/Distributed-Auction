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
