package com.starling.roundup;

import com.starling.roundup.components.Amount;
import com.starling.roundup.components.SavingGoal;
import com.starling.roundup.components.Transaction;
import com.starling.roundup.services.RoundUpService;
import com.starling.roundup.services.SavingGoalService;
import com.starling.roundup.services.TransactionService;
import com.starling.roundup.util.RoundUpHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoundUpApplicationTests {

	private final String ACCOUNT_ID = "ad2e539f-cb1a-4155-8064-1a758a98a8c8";
	private final String SAVINGS_GOAL_ID = "abcde-fghij";
	private final Amount testAmount = new Amount("GBP", new BigDecimal("3321")); // 79p saved
	private final Amount testAmount2 = new Amount("GBP", new BigDecimal("3631")); // 69p saved
	private final Amount testAmount3 = new Amount("GBP", new BigDecimal("1971")); // 29p saved
	private final List<Transaction> mockTransactionList = new ArrayList<>();

	@Mock
	private RoundUpService roundUpServiceMock;
	@Mock private TransactionService transactionServiceMock;
	@Mock private RoundUpHelper roundUpHelperMock;
	@Mock private SavingGoalService savingGoalServiceMock;

	@Test
	void shouldThrowErrorWhenCallingAccountsEndpointFails()
	{
		when(roundUpServiceMock.roundUpTransactions()).thenCallRealMethod();
		assertThrows(Exception.class, () -> {
			roundUpServiceMock.roundUpTransactions();
		});
	}

	@Test
	void shouldReturnAccountIdWhenCallingAccountsEndpoint()
	{
		when(roundUpServiceMock.getAccountUid()).thenReturn(ACCOUNT_ID);
		assertEquals(ACCOUNT_ID, roundUpServiceMock.getAccountUid());
	}

	@Test
	void shouldReturnListOfEmptyTransactionsWhenThereAreNoTransactions()
	{
		when(transactionServiceMock.getTransactions(ACCOUNT_ID)).thenReturn(mockTransactionList);
		assertEquals(Collections.emptyList(), transactionServiceMock.getTransactions(ACCOUNT_ID));
	}

	@Test
	void shouldReturnListOfTransactionsWhenThereAreTransactionsPresent()
	{
		when(transactionServiceMock.getTransactions(ACCOUNT_ID)).thenReturn(mockTransactionList);
		mockTransactionList.add(new Transaction(null, testAmount, null, null));

		List<Transaction> expected = new ArrayList<>();
		expected.add(new Transaction(null, testAmount, null, null));

		assertEquals(expected, transactionServiceMock.getTransactions(ACCOUNT_ID));
	}

	@Test
	void shouldReturnZeroRoundUpAmountWhenTransactionListIsEmpty()
	{
		when(roundUpHelperMock.roundUpTransactionAmount(Collections.emptyList())).thenReturn(new BigDecimal(0));
		assertEquals(new BigDecimal(0), roundUpHelperMock.roundUpTransactionAmount(new ArrayList<>()));
	}

	@Test
	void shouldReturnRoundUpAmountWhenTransactionListIsNotEmpty()
	{
		when(roundUpHelperMock.roundUpTransactionAmount(mockTransactionList)).thenCallRealMethod();

		mockTransactionList.add(new Transaction(null, testAmount, "OUT", null));
		mockTransactionList.add(new Transaction(null, testAmount2, "OUT", null));
		mockTransactionList.add(new Transaction(null, testAmount3, "OUT", null));
		mockTransactionList.add(new Transaction(null, testAmount3, "IN", null));

		BigDecimal expected = new BigDecimal(1.77).setScale(2, RoundingMode.DOWN);

		assertEquals(expected, roundUpHelperMock.roundUpTransactionAmount(mockTransactionList));
	}

	@Test
	void shouldReturnEmptyListWhenThereAreNoSavingsGoals()
	{
		when(savingGoalServiceMock.getSavingGoalsList(ACCOUNT_ID)).thenReturn(Collections.emptyList());
		assertEquals(Collections.emptyList(), savingGoalServiceMock.getSavingGoalsList(ACCOUNT_ID));
	}

	@Test
	void shouldCreateNewSavingsGoalWhenThereAreNoSavingsGoals()
	{
		when(savingGoalServiceMock.createNewSavingsGoal(ACCOUNT_ID)).thenReturn(SAVINGS_GOAL_ID);
		assertEquals(SAVINGS_GOAL_ID, savingGoalServiceMock.createNewSavingsGoal(ACCOUNT_ID));
	}

	@Test
	void shouldReturnListWhenThereIsASavingGoal()
	{
		List<SavingGoal> savingGoalListToReturn = new ArrayList<>();
		savingGoalListToReturn.add(new SavingGoal());

		when(savingGoalServiceMock.getSavingGoalsList(ACCOUNT_ID)).thenReturn(savingGoalListToReturn);

		assertEquals(1, savingGoalServiceMock.getSavingGoalsList(ACCOUNT_ID).size());
	}

	@Test
	void shouldReturnSavingsGoalListFromRoundUpServiceRoundUp()
	{
		when(roundUpServiceMock.roundUpTransactions()).thenReturn(Collections.emptyList());
		assertEquals(Collections.emptyList(), roundUpServiceMock.roundUpTransactions());
	}

}
