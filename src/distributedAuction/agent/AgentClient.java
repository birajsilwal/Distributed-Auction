package distributedAuction.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AgentClient extends Thread {

    private String ip;
    private int port;
    private Agent agent;
    private PrintWriter out;
    private BufferedReader in;

    public AgentClient(String ip, int port, Agent agent) throws IOException {
        this.ip = ip;
        this.port = port;
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
            switch (inputs[0]){
                case "newAH:":
                    agent.auctionHouses.add(Integer.parseInt(inputs[1]));
                    System.out.println("New Auction House Available");
                    System.out.println(agent.auctionHouses.size()+" Auction Houses Available");
                    break;
                case "accountNumber":
                    agent.accountNum = Integer.parseInt(inputs[1]);
                    System.out.println("Account number: "+ agent.accountNum);
                    agent.registered = true;
                    break;
                case "deregistered":
                    agent.registered = false;
                    agent.terminate();
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
