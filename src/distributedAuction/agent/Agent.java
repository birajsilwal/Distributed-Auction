package distributedAuction.agent;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Agent{
    // these variables store info about the agent itself
    private static Agent agent;
    private String name;
    private double balance;
    private int accountNum;
    private boolean registered;

    // these variables handle communication with Bank and Auction Houses
    private AgentClient bankClient;
    private AgentClient ahClient;

    // these variables store info about the auction houses
    private LinkedList<Integer> auctionHousePorts = new LinkedList<>();
    private static LinkedList<String> itemList = new LinkedList<>();

    // constructor for Agent object
    public Agent(String name, double balance) throws IOException{
        final int BANK_PORT = 4444;
        bankClient = new AgentClient("10.20.10.242", BANK_PORT);
        bankClient.start();
        this.name = name;
        this.balance = balance;
        try {
            bankClient.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        sendMessage(bankClient, "agent: "+name+" balance: "+balance);
    }

    public static void main(String args[]) throws IOException{
        //create agent with user input from console
        agent = createAgent();
        System.out.println("You have established the following agent: " + agent);

        //enter main menu
        agent.mainMenu();

        String inputLine = null;
        do {
            agent.processInput(inputLine);
            if (inputLine != null && inputLine.equals("closed")) {
                break;
            }
            try{
                inputLine = agent.bankClient.getInput().readLine();
            }catch(IOException ex) {
                inputLine = null;
            }
        }
        while(inputLine != null);
    }

    private static Agent createAgent() throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter agent name.");
        String name = scanner.nextLine();
        System.out.println("Agent name is " + name + ". Please enter starting balance.");
        double balance = scanner.nextDouble();
        return new Agent(name, balance);
    }

    private void mainMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a number to perform the associated action.");
        System.out.println("Input 0 to terminate session.");
        System.out.println("Input 1 to see available auction houses.");
        System.out.println("Input 2 to deposit funds into your account.");
        int selection = scanner.nextInt();

        switch (selection){
            case 0:
                //need to add check to make sure no outstanding auctions
                agent.terminate();
                break;
            case 1:
                agent.ahSelect();
                break;
            case 2:
                System.out.println("How much would you like to deposit?");
                int deposit = scanner.nextInt();
                sendMessage(bankClient,"deposit " + deposit);
                System.out.println("Deposited $" + deposit + " into your account.");
                agent.mainMenu();
                break;
            default:
                System.out.println("Invalid selection, please try again.");
                agent.mainMenu();
                break;
        }
    }

    private void ahSelect(){
        int counter = 1;
        System.out.println("The following auction houses are available: ");
        for(int ah : auctionHousePorts){
            System.out.println(counter + ". " + ah);
            counter++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a number to connect to the associated auction house.");
        System.out.println("Input 0 to return to main menu.");
        int ahSelect = scanner.nextInt();

        //this selection returns user to main menu
        if(ahSelect == 0){
            agent.mainMenu();
        }

        //this selection takes user to the AH menu for the selected AH
        else if (ahSelect > 0 && ahSelect <= auctionHousePorts.size()){
            agent.connectToAH("localHost", auctionHousePorts.get(ahSelect - 1));
            agent.ahMenu(auctionHousePorts.get(ahSelect - 1));
        }

        //this catches any invalid selection and re-displays the list of AHs
        else{
            System.out.println("Invalid selection, please try again.");
            agent.ahSelect();
        }
    }

    private void ahMenu(int ah){
        int counter = 1;
        //itemList = ah.getItems;
        System.out.println("The following items are available for bid: ");
        for(String item : itemList){
            System.out.println(counter + ". " + item);
            counter++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a number to choose an item to bid on.");
        System.out.println("Otherwise input 0 to disconnect from this auction house.");
        int itemSelect = Integer.parseInt(scanner.nextLine());

        //this selection takes user back to pick different AH
        if(itemSelect == 0){
            agent.mainMenu();
        }

        //this selection takes the user to the item menu for the selected item
        else if (itemSelect > 0 && itemSelect <= itemList.size()){
            agent.itemMenu(itemList.get(itemSelect - 1), ah);
        }

        //this catches any invalid selection and resets the menu for the current auction house
        else{
            System.out.println("Invalid selection, please try again.");
            agent.ahMenu(ah);
        }
    }

    private void itemMenu(String item, int ah){
        System.out.println("You have selected the following item: " + item);
        double currentBid = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("The current bid is [item price]. How much would you like to bid?");
        System.out.println("Otherwise input 0 to go back to item select.");
        double bid = Double.parseDouble(scanner.nextLine());

        //this selection takes user back to pick different item
        if(bid == 0){
            agent.ahMenu(ah);
        }

        //catches if insufficient funds to make selection
        else if (bid > balance){
            System.out.println("Insufficient funds in account to make this bid. Please try again.");
            agent.itemMenu(item, ah);
        }

        //catches if bid is not high enough to beat current bid
        else if (bid < currentBid){
            System.out.println("You must bid higher than the current bid of " + currentBid + ". Please try again.");
        }

        //this condition is triggered if the bid is valid
        else{
            placeBid(item, bid);
        }
    }

    private void connectToAH(String ip, int port){
        ahClient = new AgentClient(ip, port);
        ahClient.start();
        try {
            ahClient.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void placeBid(String item, double bid){
        sendMessage(bankClient, "bid " + item + " " + bid);
    }

    private void blockFunds(double amount){
        sendMessage(bankClient, "block " + amount);
    }

    private void transferFunds(double amount, String ah){
        sendMessage(bankClient, "transfer " + amount + " " + ah);
    }

    private void deregister(){
        sendMessage(bankClient,"deregister " + accountNum);
    }

    private void terminate(){
        if(registered){
            deregister();
        }
        else{
            System.exit(0);
        }
    }

    public String toString(){
        return name + ": " + balance;
    }

    private void processInput(String input){
        if(input != null){
            String[] inputs = input.split(" ");
            switch (inputs[0]){
                case "newAH:":
                    auctionHousePorts.add(Integer.parseInt(inputs[1]));
                    System.out.println("New Auction House Available");
                    System.out.println(auctionHousePorts.size()+" Auction Houses Available");
                    break;
                case "accountNumber":
                    accountNum = Integer.parseInt(inputs[1]);
                    System.out.println("Account number: "+accountNum);
                    agent.registered = true;
                    break;
                case "deregistered":
                    agent.registered = false;
                    agent.terminate();
            }
        }
    }

    private void sendMessage(AgentClient recipient, String message){
        recipient.getOutput().println(message);
    }
}
