package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*{
  "savingsGoalUid": "77887788-7788-7788-7788-778877887788",
  "success": true,
  "errors": [
    {
      "message": "Something about the error"
    }
  ]
}*/
@Data
public class CreateSavingGoal {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;
    @JsonProperty("success")
    private Boolean success;

}
