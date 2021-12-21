package com.starling.roundup.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.starling.roundup.components.SavingGoal;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingGoalWrapper
{

    @JsonProperty("savingsGoalList")
    private List<SavingGoal> savingsGoalList;

}
