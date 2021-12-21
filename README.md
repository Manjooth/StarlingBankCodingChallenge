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
- [x] Get list of API Urls to use and test using Postman
- [x] Create `TransactionService` to get `List` of `Transaction` to get transactions from the last week, using `/feed/account/{accountUid}/settled-transactions-between` endpoint and passing in arguments for dates by making call to `/feed/account/{accountUid}/settled-transactions-between?minTransactionTimestamp={minTransactionTimestamp}&maxTransactionTimestamp={maxTransactionTimestamp}`
````
e.g.
{
    "feedItems": [
        {
            "feedItemUid": "fd514cfd-cb3d-4e96-b024-abdd146fb371",
            "categoryUid": "bd96ee89-2bb9-4932-a34a-e8668ccff24b",
            "amount": {
                "currency": "GBP",
                "minorUnits": 3321
            },
            "sourceAmount": {
                "currency": "GBP",
                "minorUnits": 3321
            },
            "direction": "IN",
            "updatedAt": "2021-12-16T15:45:10.681Z",
            "transactionTime": "2021-12-16T15:45:10.000Z",
            "settlementTime": "2021-12-16T15:45:10.000Z",
            "source": "FASTER_PAYMENTS_IN",
            "status": "SETTLED",
            "counterPartyType": "SENDER",
            "counterPartyName": "Faster payment",
            "counterPartySubEntityName": "",
            "counterPartySubEntityIdentifier": "600522",
            "counterPartySubEntitySubIdentifier": "20025033",
            "reference": "Ref: 7295072450",
            "country": "GB",
            "spendingCategory": "INCOME",
            "hasAttachment": false,
            "hasReceipt": false
        },
        {
            "feedItemUid": "fd512b43-2b76-47ff-a31b-8edd340e19bc",
            "categoryUid": "bd96ee89-2bb9-4932-a34a-e8668ccff24b",
            "amount": {
                "currency": "GBP",
                "minorUnits": 3631
            },
            "sourceAmount": {
                "currency": "GBP",
                "minorUnits": 3631
            },
            "direction": "IN",
            "updatedAt": "2021-12-16T15:45:10.525Z",
            "transactionTime": "2021-12-16T15:45:10.000Z",
            "settlementTime": "2021-12-16T15:45:10.000Z",
            "source": "FASTER_PAYMENTS_IN",
            "status": "SETTLED",
            "counterPartyType": "SENDER",
            "counterPartyName": "Faster payment",
            "counterPartySubEntityName": "",
            "counterPartySubEntityIdentifier": "600522",
            "counterPartySubEntitySubIdentifier": "20025033",
            "reference": "Ref: 9246109671",
            "country": "GB",
            "spendingCategory": "INCOME",
            "hasAttachment": false,
            "hasReceipt": false
        }
}
````
- [x] Create `RoundUpService` where bulk of processing will take place
  * [x] Create `RoundUp` class to loop through `Transaction` items and total up the change left over from the `minorUnits` of the `Amount`
- [x] Create `SavingGoalService` to add savings to savings goal using `/account/{accountUid}/savings-goals` and `/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}`. If no goal exists, create one and add the amount to it
```
E.g. list of empty savings goals
{
    "savingsGoalList": []
}

E.g. populated savings goals
{
    "savingsGoalList": [
        {
            "savingsGoalUid": "d3ecb50c-8ff0-41bc-bc66-f40df9dd78d4",
            "name": "My savings goal 0.6195278725379234",
            "target": {
                "currency": "GBP",
                "minorUnits": 100000
            },
            "totalSaved": {
                "currency": "GBP",
                "minorUnits": 545
            },
            "savedPercentage": 0
        }
    ]
}
```
  * [x] Return updated list of savings goal
- [x] Get `AccountUID` from `/accounts` endpoint and pass through account id to services for making calls
```
E.g. account response
{
    "accounts": [
        {
            "accountUid": "32af7fea-cc61-4cac-80b4-20aa52a77445",
            "accountType": "PRIMARY",
            "defaultCategory": "bd96ee89-2bb9-4932-a34a-e8668ccff24b",
            "currency": "GBP",
            "createdAt": "2021-12-16T15:44:19.655Z",
            "name": "Personal"
        }
    ]
}
```
  
### Result
![Screenshot from 2021-12-21 12-56-12](https://user-images.githubusercontent.com/21277038/146933743-5a37aef1-0dca-4eff-bac0-20cc72176f9f.png)
