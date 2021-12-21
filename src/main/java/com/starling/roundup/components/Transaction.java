package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction
{

    @JsonProperty("categoryUid")
    private String categoryUid;
    @JsonProperty("sourceAmount")
    private Amount balance;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("source")
    private String source;

}
