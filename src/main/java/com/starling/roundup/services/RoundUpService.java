package com.starling.roundup.services;

import com.starling.roundup.components.SavingGoal;
import com.starling.roundup.components.Transaction;
import com.starling.roundup.config.HeadersConfiguration;
import com.starling.roundup.exceptions.StarlingException;
import com.starling.roundup.util.APIUrls;
import com.starling.roundup.util.RoundUpHelper;
import com.starling.roundup.wrappers.AccountWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RoundUpService.class);

    @Autowired private TransactionService transactionService;
    @Autowired private RoundUpHelper roundUpHelper;
    @Autowired private SavingGoalService savingGoalService;

    @Autowired private RestTemplate restTemplate;
    @Autowired private HeadersConfiguration headersConfiguration;

    public List<SavingGoal> roundUpTransactions()
    {
        try
        {
            String savingsGoalUID = "";
            final String accountId = this.getAccountUid();
            final List<Transaction> transactionsList = this.transactionService.getTransactions(accountId);
            final BigDecimal roundedAmount = this.roundUpHelper.roundUpTransactionAmount(transactionsList);
            final List<SavingGoal> savingGoalsList = this.savingGoalService.getSavingGoalsList(accountId);

            if(savingGoalsList.isEmpty())
            {
                savingsGoalUID = this.savingGoalService.createNewSavingsGoal(accountId);
            }

            savingsGoalUID = savingGoalsList.get(0).getSavingsGoalUid(); // get first item in the list

            this.savingGoalService.updateSavingsGoal(accountId, savingsGoalUID, roundedAmount);

            return this.savingGoalService.getSavingGoalsList(accountId);

        }
        catch(StarlingException starlingException)
        {
            LOGGER.error("Error completing rounding up of transactions for " + getAccountUid());
            throw new RuntimeException("Error with fetching data");
        }
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
