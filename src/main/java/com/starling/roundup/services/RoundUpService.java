package com.starling.roundup.services;

import com.starling.roundup.components.SavingGoal;
import com.starling.roundup.components.Transaction;
import com.starling.roundup.config.HeadersConfiguration;
import com.starling.roundup.util.APIUrls;
import com.starling.roundup.util.RoundUp;
import com.starling.roundup.wrappers.AccountWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class RoundUpService
{

    @Autowired private TransactionService transactionService;
    @Autowired private RoundUp roundUp;
    @Autowired private SavingGoalService savingGoalService;
    @Autowired private RestTemplate restTemplate;
    @Autowired private HeadersConfiguration headersConfiguration;


    public List<SavingGoal> roundUp()
    {
        String savingsGoalUID = "";
        final String accountId = this.getAccountUid();
        final List<Transaction> transactionsList = this.transactionService.getTransactions(accountId);
        final BigDecimal roundedAmount = this.roundUp.roundUpTransactionAmount(transactionsList);
        final List<SavingGoal> savingGoalsList = this.savingGoalService.getSavingGoalsList(accountId);

        if(savingGoalsList.isEmpty())
        {
            savingsGoalUID = this.savingGoalService.createNewSavingsGoal(accountId);
        }

        for (SavingGoal savingGoal : savingGoalsList)
        {
            savingsGoalUID = savingGoal.getSavingsGoalUid();
        }

        this.savingGoalService.updateSavingsGoal(accountId, savingsGoalUID, roundedAmount);

        return this.savingGoalService.getSavingGoalsList(accountId);
    }

    public String getAccountUid()
    {
        final HttpEntity<AccountWrapper> request = this.getAccountInfoCustomHeaders();
        final AccountWrapper wrapper = this.restTemplate
                .exchange(APIUrls.ACCOUNT_INFO_URL,
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<AccountWrapper>() {})
                .getBody();

        return Objects.requireNonNull(wrapper).getAccounts().get(0).getAccountId();
    }

    private HttpEntity<AccountWrapper> getAccountInfoCustomHeaders()
    {
        final HttpHeaders httpHeaders = this.headersConfiguration.getHeaders();
        return new HttpEntity<>(httpHeaders);
    }

}
