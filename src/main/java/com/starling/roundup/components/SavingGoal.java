package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Currency;

/*{
  "savingsGoalList": [
    {
      "savingsGoalUid": "77887788-7788-7788-7788-778877887788",
      "name": "Trip to Paris",
      "target": {
        "currency": "GBP",
        "minorUnits": 123456
      },
      "totalSaved": {
        "currency": "GBP",
        "minorUnits": 123456
      },
      "savedPercentage": 100
    }
  ]
}*/
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
