package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SavingGoal {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("totalSaved")
    private Amount amount;
    @JsonProperty("savedPercentage")
    private String percentageSaved;

}
