package distributedAuction.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AgentClient extends Thread {

    private String ip;
    private Agent agent;
    private PrintWriter out;
    private BufferedReader in;

    public AgentClient(String ip, int port, Agent agent) throws IOException {
        this.ip = ip;
        this.agent = agent;
        Socket socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run(){
        String inputLine = null;
        do {
            processInput(inputLine);
            if (inputLine != null && inputLine.equals("closed")) {
                break;
            }
            try{
                inputLine = in.readLine();
            }
            catch(IOException ex) {
                inputLine = null;
            }
        }
        while(inputLine != null);
    }

    private void processInput(String input){
        if(input != null){
            String[] inputs = input.split(" ");
            Double bid = Double.parseDouble(inputs[1]);
            String item = inputs[2];
            switch (inputs[0]){
                case "newAH:":
                    agent.auctionHouseIPs.add(inputs[1]);
                    System.out.println("New Auction House Available");
                    System.out.println(agent.auctionHouseIPs.size()+" Auction Houses Available");
                    break;
                case "accountNumber:":
                    agent.accountNum = Integer.parseInt(inputs[1]);
                    System.out.println("Account number: "+ agent.accountNum);
                    agent.registered = true;
                    break;
                case "accept:":
                    agent.blockFunds(bid);
                    System.out.println("Your bid of " + bid + " for " + item +
                            "was accepted. You will be notified if you win.");
                    break;
                case "reject:":
                    agent.unblockFunds(bid);
                    System.out.println("Your bid of " + bid + " for " + item +
                            "was rejected. Please bid on another item.");
                    System.out.println("Your money will be refunded. Please bid on another item.");
                    break;
                case "outbid:":
                    Double highBid = Double.parseDouble(inputs[2]);
                    agent.unblockFunds(bid);
                    System.out.println("Your bid of " + bid + " for " + item +
                            "was just outbid with a bid of " + highBid + ".");
                    System.out.println("Your money will be refunded. Please bid on another item.");
                    break;
                case "winner:":
                    agent.transferFunds(bid, ip);
                    System.out.println("Your bid of " + bid + " for " + item + " was the highest bid.");
                    System.out.println("You have won the auction. Your money will be transferred to the auction house. " +
                            "Please bid on another item.");
                    break;
            }
        }
    }

    public PrintWriter getOutput(){
        return out;
    }

    public BufferedReader getInput(){
        return in;
    }
}
