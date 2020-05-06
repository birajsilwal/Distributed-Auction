package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class BankClient implements Runnable{
    private ClientType clientType;
    private Account account;
    private BufferedReader in;
    private PrintWriter out;
    private Map<Integer, Account> houseAccounts;

    public BankClient(ClientType clientType, BufferedReader in, PrintWriter out, Account account, double startingBalance,
                      Map<Integer, Account> houseAccounts){
        this.clientType = clientType;
        this.in = in;
        this.out = out;
        this.account = account;
        this.houseAccounts = houseAccounts;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    public BankClient(ClientType clientType, BufferedReader in, PrintWriter out, Account account, int startingBalance){
        this.clientType = clientType;
        this.in = in;
        this.out = out;
        this.account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void processInput(String inputLine){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
            switch (input[0]){
                case "deposit":
                    account.deposit(Integer.parseInt(input[1]));
                    break;
                case "blockFunds":
                    account.blockFunds(Integer.parseInt(input[1]));
                    break;
                case "withdrawFunds":
                    account.withdraw(Integer.parseInt(input[1]));
                    break;
                case "transfer":
                    int portNumber = Integer.parseInt(input[3]);
                    System.out.println("Starting Balance: "+houseAccounts.get(portNumber).getAvailableBalance());
                    //withdraw the funds from the starting account
                    account.withdraw(Integer.parseInt(input[1]));
                    //deposit funds into the ending account
                    houseAccounts.get(portNumber).deposit(Integer.parseInt(input[1]));
                    break;
            }
        }
    }

    @Override
    public void run() {
        String inputLine = null;
        do {
            processInput(inputLine);
            if (inputLine != null && inputLine.equals("closed")) {
                break;
            }
            try{
                inputLine = in.readLine();
            }catch(IOException ex) {
                inputLine = null;
            }
        }while(inputLine != null);
    }

    public PrintWriter getOut() {
        return out;
    }
}