package com.starling.roundup.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.starling.roundup.components.Amount;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingGoalResponseWrapper {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;
    private String name;
    @JsonProperty(value = "totalSaved")
    private Amount amount;

}
