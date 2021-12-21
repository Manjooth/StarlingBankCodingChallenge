package com.starling.roundup.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.starling.roundup.components.Transaction;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionsWrapper implements Serializable
{

    @JsonProperty("feedItems")
    private List<Transaction> transactions;

}
