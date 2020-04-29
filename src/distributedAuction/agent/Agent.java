package agent;

import java.util.LinkedList;
import java.util.Scanner;

public class Agent {
    private String name;
    private double balance;

    private static Agent agent;
    private int accountNum;

    private static LinkedList<String> auctionHouses;
    private static LinkedList<String> itemList;

    public Agent(String name, double balance){
        this.name = name;
        this.balance = balance;
    }

    public static void main(String args[]){
        //create agent with user input from console
        agent = createAgent();
        System.out.println("You have established the following agent: " + agent);

        //open bank account
        agent.openAccount();

        //get account number
        //accountNumber = bank.getAccountNumber;

        //enter main menu
        agent.mainMenu();
    }

    private static Agent createAgent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter agent name.");
        String name = scanner.nextLine();
        System.out.println("Agent name is " + name + ". Please enter starting balance.");
        double balance = scanner.nextDouble();
        return new Agent(name, balance);
    }

    private void mainMenu(){
        int counter = 1;
        //auctionHouses = bank.getAuctionHouses;
        System.out.println("The following auction houses are available: ");
        for(String ah : auctionHouses){
            System.out.println(counter + ". " + ah);
            counter++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a number to connect to the associated auction house.");
        System.out.println("Otherwise input 0 to terminate session.");
        int ahSelect = scanner.nextInt();

        //this selection terminates the program
        if(ahSelect == 0){
            //need to add check to make sure no outstanding auctions

            agent.terminate();
        }

        //this selection takes user to the AH menu for the selected AH
        else if (ahSelect > 0 && ahSelect <= auctionHouses.size()){
            agent.ahMenu(auctionHouses.get(ahSelect - 1));
        }

        //this catches any invalid selection and re-displays the list of AHs
        else{
            System.out.println("Invalid selection, please try again.");
            agent.mainMenu();
        }
    }

    private void ahMenu(String ah){
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
            agent.itemMenu(itemList.get(itemSelect - 1));
        }

        //this catches any invalid selection and resets the menu for the current auction house
        else{
            System.out.println("Invalid selection, please try again.");
            agent.ahMenu(ah);
        }
    }

    private void itemMenu(String item){
        System.out.println("You have selected the following item: " + item);
        double currentBid = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("The current bid is [item price]. How much would you like to bid?");
        System.out.println("Otherwise input 0 to go back to item select.");
        double bid = Double.parseDouble(scanner.nextLine());

        //this selection takes user back to pick different item
        if(bid == 0){
            agent.mainMenu();
        }

        //catches if insufficient funds to make selection
        else if (bid > balance){
            System.out.println("Insufficient funds in account to make this bid. Please try again.");
            agent.itemMenu(item);
        }

        //catches if bid is not high enough to beat current bid
        else if (bid < currentBid){
            System.out.println("You must bid higher than the current bid of " + currentBid + ". Please try again.");
        }

        //this condition is triggered if the bid is valid
        else{
            blockFunds(bid);
            placeBid(item, bid);
        }
    }

    private void placeBid(String item, double bid){

    }

    private void openAccount(){

    }

    private void blockFunds(double amount){

    }

    private void transferFunds(){

    }

    private void deregister(){

    }

    private void terminate(){

    }

    public String toString(){
        return name + ": " + balance;
    }
}
