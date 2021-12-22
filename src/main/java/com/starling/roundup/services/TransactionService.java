package com.starling.roundup.services;

import com.starling.roundup.wrappers.TransactionsWrapper;
import com.starling.roundup.components.Transaction;
import com.starling.roundup.config.HeadersConfiguration;
import com.starling.roundup.util.APIUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TransactionService
{

    @Autowired private RestTemplate restTemplate;
    @Autowired private HeadersConfiguration headersConfiguration;

    public List<Transaction> getTransactions(final String accountId)
    {
        final Map<String, String> params = new HashMap<>();

        params.put("accountUid", accountId);
        params.put("minTransactionTimestamp", getDate(true));
        params.put("maxTransactionTimestamp", getDate(false));

        final HttpEntity<TransactionsWrapper> request = this.getHeaders();

        final ResponseEntity<TransactionsWrapper> response = this.restTemplate.exchange(APIUrls.WEEKLY_TRANSACTIONS_URL,
                HttpMethod.GET,
                request,
                TransactionsWrapper.class, params);

        if(response.getBody() == null)
        {
            return Collections.emptyList();
        }

        return response.getBody().getTransactions();
    }

    public String getDate(final boolean isWeekAgoDate){
        final SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        final Date currentDate = new Date();
        Timestamp timestamp;

        if(!isWeekAgoDate){
            timestamp =new Timestamp(currentDate.getTime());
        }else{
            final LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(7);
            final Date currentMinusSevenDays = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            timestamp =new Timestamp(currentMinusSevenDays.getTime());
        }


        return today.format(timestamp);
    }

    private HttpEntity<TransactionsWrapper> getHeaders()
    {
        final HttpHeaders httpHeaders = this.headersConfiguration.getHeaders();
        return new HttpEntity<>(httpHeaders);
    }

}
