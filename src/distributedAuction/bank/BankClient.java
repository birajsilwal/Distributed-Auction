package distributedAuction.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BankClient implements Runnable{
    private Account Account;
    private BufferedReader in;
    private PrintWriter out;

    public BankClient(BufferedReader in, PrintWriter out, Account account, int startingBalance){
        this.in = in;
        this.out = out;
        Account = account;
        account.deposit(startingBalance);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void processInput(String inputLine){
        if(inputLine != null){
            String[] input = inputLine.split(" ");
            switch (input[0]){
                case "deposit":
                    Account.deposit(Integer.parseInt(input[1]));
                    break;
                case "blockFunds":
                    Account.blockFunds(Integer.parseInt(input[1]));
                    break;
                case "transfer":
                    //Still working through method
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
