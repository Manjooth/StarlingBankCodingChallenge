package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class Account {

    @JsonProperty("accountUid")
    private String accountId;
    @JsonProperty("defaultCategory")
    private String defaultCategory;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

}
