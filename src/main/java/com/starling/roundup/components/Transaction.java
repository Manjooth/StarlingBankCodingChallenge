package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

/*
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
        }
*/

@Data
public class Transaction {

    @JsonProperty("categoryUid")
    private String categoryUid;
    @JsonProperty("sourceAmount")
    private Amount balance;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("source")
    private String source;

}
