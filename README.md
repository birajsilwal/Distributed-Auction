# Distributed Auction

## Collaborators: Trey Sampson, Charley Bickel, Biraj Silwal 

### Introduction

Distributed Auction is the simulation of a system of multiple auction houses selling items, multiple agents buying 
items, and a bank to keep track of everyone’s funds. The bank will exist on one machine at a static known address, 
the agents and auction houses will be dynamically created on other machines.

### Bank

The bank is static and at a known address. You’ll start this program before either agents or auction houses. (The bank is a server and the agents and auction houses are its clients.)

When an agent bids on or is outbid in an auction, the bank will block or unblock the appropriate amount of funds, at the request of the auction house. When an agent wins an auction, the bank will transfer these blocked funds from the agent to the auction house account, at the request of the agent. 

Auction houses provide the bank with their host and port information. The bank provides the agents with the list of the auction houses and their addresses so the agents will be able to connect directly to the auction houses. 

### Auction House

Each auction house is dynamically created. Upon creation, it registers with the bank, opening an account with zero balance. It also provides the bank with its host and port address, so the bank can inform the agents of the existance of this auction house. (An auction house is a client of the bank, but also is a server with agents as its clients.) It hosts a list of items being auctioned and tracks the current bidding status of each item.

Upon request, it shares the list of items being auctioned and the bidding status with agents, including for each item house id, item id, description, minimum bid and current bid. 

The user may terminate the program when no bidding activity is in progress. The program should not allow exit when there are still bids to be resolved.

### Agent

Each agent is dynamically created. Upon creation, it opens a bank account by providing a name and an initial balance, and receives a unique account number. (The agent is a client of both the bank and the auction houses.) 

The agent gets a list of active auction houses from the bank. In connects to an auction house using the host and port information sent from the bank. The agent receives a list of items being auctioned from the auction house. 

When an agent makes a bid on an item, it receives back one or more status messages as the auction proceeds: 

* acceptance – The bid is the current high bid 
* rejection – The bid was invalid, too low, insufficient funds in the bank, etc. 
* outbid – Some other agent has placed a higher bid 
* winner – The auction is over and this agent has won.

The agent notiﬁes the bank to transfer the blocked funds to the auction house after it wins a bid. 

The program may terminate when no bidding activity is in progress. The program should not allow exit when there are still bids to be resolved. At termination, it deregisters with the bank. An agent terminating should not break the behaviour of any other programs in the system.

### Auction Rules

The auction house receives bids and acknowledges them with a reject or accept response.

* When a bid is accepted, the bank is requested to block those funds. In fact, the bid should not be accepted if there are not enough available funds in the bank.
* When a bid is overtaken, an outbid notiﬁcation is sent to the agent and the funds are unblocked.
* A bid is successful if not overtaken in 30 seconds. When winning a bid, the agent receives a winner notification and the auction house waits for the blocked funds to be transferred into its account. 
* If there has been no bid placed on an item, the item remains listed for sale.

### Implementation

### Docs

* Object-Oriented Design file is inside the Docs folder

### Known Bugs








