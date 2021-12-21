package com.starling.roundup.controller;

import com.starling.roundup.components.Amount;
import com.starling.roundup.components.SavingGoal;
import com.starling.roundup.components.Transaction;
import com.starling.roundup.services.RoundUpService;
import com.starling.roundup.services.SavingGoalService;
import com.starling.roundup.services.TransactionService;
import com.starling.roundup.util.RoundUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoundUpControllerTest
{

    private final String ACCOUNT_ID = "ad2e539f-cb1a-4155-8064-1a758a98a8c8";
    private final String SAVINGS_GOAL_ID = "abcde-fghij";
    private final Amount testAmount = new Amount("GBP", new BigDecimal("3321")); // 79p saved
    private final Amount testAmount2 = new Amount("GBP", new BigDecimal("3631")); // 69p saved
    private final Amount testAmount3 = new Amount("GBP", new BigDecimal("1971")); // 29p saved
    private final List<Transaction> mockTransactionList = new ArrayList<>();

    @Autowired RoundUpService roundUpService;
    @Mock private TransactionService transactionService;
    @Mock private RoundUp roundUp;
    @Mock SavingGoalService savingGoalService;

    @BeforeEach
    void setUp() {
        when(transactionService.getTransactions(ACCOUNT_ID)).thenReturn(mockTransactionList);
        when(roundUp.roundUpTransactionAmount(Collections.emptyList())).thenReturn(new BigDecimal(0));
        when(savingGoalService.getSavingGoalsList(ACCOUNT_ID)).thenReturn(Collections.emptyList());
        when(savingGoalService.createNewSavingsGoal(ACCOUNT_ID)).thenReturn(SAVINGS_GOAL_ID);
    }

    @Test
    void shouldThrowErrorWhenAccountIdCallingAccountEndpointFails()
    {
        //TODO
    }

    @Test
    void shouldReturnAccountIdWhenCallingAccountsEndpoint()
    {
        assertEquals(ACCOUNT_ID, roundUpService.getAccountUid());
    }

    @Test
    void shouldThrowErrorWhenCallingTransactionEndpointFails()
    {
        //TODO
    }

    @Test
    void shouldReturnListOfEmptyTransactionsWhenThereAreNoTransactions()
    {
        assertEquals(Collections.emptyList(), transactionService.getTransactions(ACCOUNT_ID));
    }

    @Test
    void shouldReturnListOfTransactionsWhenThereAreTransactionsPresent()
    {
        mockTransactionList.add(new Transaction(null, testAmount, null, null));

        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(null, testAmount, null, null));

        assertEquals(expected, transactionService.getTransactions(ACCOUNT_ID));
    }

    @Test
    void shouldReturnZeroRoundUpAmountWhenTransactionListIsEmpty()
    {
        assertEquals(new BigDecimal(0), roundUp.roundUpTransactionAmount(new ArrayList<>()));
    }

    @Test
    void shouldReturnRoundUpAmountWhenTransactionListIsNotEmpty()
    {
        when(roundUp.roundUpTransactionAmount(mockTransactionList)).thenCallRealMethod();

        mockTransactionList.add(new Transaction(null, testAmount, "OUT", null));
        mockTransactionList.add(new Transaction(null, testAmount2, "OUT", null));
        mockTransactionList.add(new Transaction(null, testAmount3, "OUT", null));
        mockTransactionList.add(new Transaction(null, testAmount3, "IN", null));

        BigDecimal expected = new BigDecimal(1.77).setScale(2, RoundingMode.DOWN);

        assertEquals(expected, roundUp.roundUpTransactionAmount(mockTransactionList));
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoSavingsGoals()
    {
        assertEquals(Collections.emptyList(), savingGoalService.getSavingGoalsList(ACCOUNT_ID));
    }

    @Test
    void shouldCreateNewSavingsGoalWhenThereAreNoSavingsGoals()
    {
        assertEquals(SAVINGS_GOAL_ID, savingGoalService.createNewSavingsGoal(ACCOUNT_ID));
    }

    @Test
    void shouldReturnListWhenThereIsASavingGoal()
    {
        List<SavingGoal> savingGoalListToReturn = new ArrayList<>();
        savingGoalListToReturn.add(new SavingGoal());

        when(savingGoalService.getSavingGoalsList(ACCOUNT_ID)).thenReturn(savingGoalListToReturn);


        assertEquals(1, savingGoalService.getSavingGoalsList(ACCOUNT_ID).size());
    }
}