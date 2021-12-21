### The Challenge
We’d like you to develop a “round-up” feature for Starling customers using our public
developer API that is available to all customers and partners.

For a customer, take all the transactions in a given week and round them up to the nearest
pound. For example with spending of £4.35, £5.20 and £0.87, the round-up would be £1.58.
This amount should then be transferred into a savings goal, helping the customer save for
future adventures.

### API calls
To make this work, the key parts from our public API you will need are:
1. Accounts - To retrieve accounts for the customer
2. Transaction feed - To retrieve transactions for the customer
3. Savings Goals - Create a savings goals and transfer money to savings goals
   
We do expect to see your working here: please do not use any of the libraries out there
which provide an sdk for interacting with our api.
   
### What to build
Pick your favourite platform to develop for. If you have experience with multiple languages
that’s fantastic - you only need to submit for one and your choice won’t impact potential
roles with us - we have plenty of engineers who work across platforms / learn news ones
here!

Here’s what we imagine you will build for each platform, but you can diverge from this so
long as you are prepared to explain what you’ve done and why:

- Java: Main method which runs the round up directly, or main method which starts an
embedded web server with a REST resource we can invoke to trigger the round-up.

### Thinking/Planning


### Result
![Screenshot from 2021-12-21 12-56-12](https://user-images.githubusercontent.com/21277038/146933743-5a37aef1-0dca-4eff-bac0-20cc72176f9f.png)
