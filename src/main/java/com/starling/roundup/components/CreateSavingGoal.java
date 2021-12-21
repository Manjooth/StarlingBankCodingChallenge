package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateSavingGoal {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;
    @JsonProperty("success")
    private Boolean success;

}
