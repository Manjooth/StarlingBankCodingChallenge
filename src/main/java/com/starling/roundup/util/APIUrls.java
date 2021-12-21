package com.starling.roundup.util;

public class APIUrls
{
    public static final String BASE_URL = "https://api-sandbox.starlingbank.com/api/v2";

    // accounts
    public static final String ACCOUNT_INFO_URL =  BASE_URL + "/accounts";

    // transactions
    public static final String WEEKLY_TRANSACTIONS_URL = BASE_URL + "/feed/account/{accountUid}/settled-transactions-between?minTransactionTimestamp={minTransactionTimestamp}&maxTransactionTimestamp={maxTransactionTimestamp}";

    // savings goals
    public static final String SAVINGS_GOALS_LIST_URL = BASE_URL + "/account/{accountUid}/savings-goals";
    public static final String SAVINGS_GOALS_ADD_MONEY_URL = BASE_URL + "/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}";
}
