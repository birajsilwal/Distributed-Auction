package bank;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


public class Agent implements Runnable{
    private final Random random = new Random();
    private final int id;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private LinkedList<Integer> auctionHousePorts = new LinkedList<>();
    private LinkedList<Agent> agents = new LinkedList<>();

    public Agent() throws IOException {
        id = random.nextInt(100);
        clientSocket = new Socket("localHost", 4444);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println("agent "+id);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void processInput(String input){
        if(input != null){
            String[] inputs = input.split(" ");
            if(inputs[0].equals("newAuctionHouse:")){
                auctionHousePorts.add(Integer.parseInt(inputs[1]));
                System.out.println("New Auction House Available");
                System.out.println(auctionHousePorts.size()+" Auction Houses Available");
            }
        }
    }

    @Override
    public void run() {
        String inputLine = null;
        String outputLine;
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

    private void sendMessage(String Message){
        out.println(Message);
    }

    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        while (true){
            if(input.hasNextLine()){
                String inputLine = input.nextLine();
                switch (inputLine){
                    case "New Agent":
                        Agent agent = new Agent();
                }
            }
        }
    }
}
