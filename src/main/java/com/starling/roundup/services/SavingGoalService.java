package com.starling.roundup.services;

import com.starling.roundup.components.Amount;
import com.starling.roundup.components.CreateSavingGoal;
import com.starling.roundup.components.CreateSavingsGoalRequest;
import com.starling.roundup.components.SavingGoal;
import com.starling.roundup.config.HeadersConfiguration;
import com.starling.roundup.util.APIUrls;
import com.starling.roundup.wrappers.AmountWrapper;
import com.starling.roundup.wrappers.SavingGoalWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SavingGoalService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HeadersConfiguration headersConfiguration;

    public List<SavingGoal> getSavingGoalsList(final String accountId){
        final HttpEntity<SavingGoalWrapper> request = this.getHeaders();
        final Map<String, String> params = new HashMap<>();
        params.put("accountUid", accountId);

        final ResponseEntity<SavingGoalWrapper> response = this.restTemplate.exchange(APIUrls.SAVINGS_GOALS_LIST_URL,
                HttpMethod.GET,
                request,
                SavingGoalWrapper.class, params);

        if(response.getBody() == null){
            return Collections.emptyList();
        }

        return response.getBody().getSavingsGoalList();
    }

    public String createNewSavingsGoal(final String accountId) {
        final HttpEntity<CreateSavingsGoalRequest> request = this.putHeaders();

        final Map<String, String> params = new HashMap<>();
        params.put("accountUid", accountId);

        final ResponseEntity<CreateSavingGoal> response = this.restTemplate.exchange(APIUrls.SAVINGS_GOALS_LIST_URL,
                HttpMethod.PUT,
                request,
                CreateSavingGoal.class, params);

        return response.getBody().getSavingsGoalUid();
    }

    public void updateSavingsGoal(final String accountId, final String savingsGoalUID, final BigDecimal roundedAmount) {
        final Map<String, String> params = new HashMap<>();
        final String transferUID = UUID.randomUUID().toString();

        params.put("savingsGoalUid", savingsGoalUID);
        params.put("transferUid", transferUID);
        params.put("accountUid", accountId);

        final HttpEntity<AmountWrapper> request = this.putAmountCustomHeaders(roundedAmount);
        this.restTemplate.exchange(APIUrls.SAVINGS_GOALS_ADD_MONEY_URL, HttpMethod.PUT, request, String.class, params);
    }

    private HttpEntity<SavingGoalWrapper> getHeaders() {
        final HttpHeaders httpHeaders = this.headersConfiguration.getHeaders();
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<CreateSavingsGoalRequest> putHeaders() {
        final HttpHeaders httpHeaders = this.headersConfiguration.getHeaders();
        return new HttpEntity<>(CreateSavingsGoalRequest.withDefaultValues(), httpHeaders);
    }

    private HttpEntity<AmountWrapper> putAmountCustomHeaders(final BigDecimal roundedAmount) {
        final HttpHeaders httpHeaders = this.headersConfiguration.getHeaders();

        final AmountWrapper amount = new AmountWrapper();
        final Amount newAmount = new Amount();
        newAmount.setMinorUnits(roundedAmount.movePointRight(2));
        newAmount.setCurrency("GBP");
        amount.setAmount(newAmount);
        return new HttpEntity<>(amount, httpHeaders);
    }
}
