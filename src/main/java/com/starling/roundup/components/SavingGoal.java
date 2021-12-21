package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingGoal
{

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("totalSaved")
    private Amount amount;
    @JsonProperty("savedPercentage")
    private String percentageSaved;

}
