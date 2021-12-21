package com.starling.roundup.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSavingsGoalRequest {

    private String name;
    private String currency;
    private Amount target;
    private String base64EncodedPhoto;

/*{
  "name": "Trip to Paris",
  "currency": "GBP",
  "target": {
    "currency": "GBP",
    "minorUnits": 123456
  },
  "base64EncodedPhoto": "string"
}*/

    public static CreateSavingsGoalRequest withDefaultValues() {

        String name = "My savings goal " + new Random().nextInt(10000000);;
        String currency = "GBP";
        Amount target = new Amount(currency, BigDecimal.valueOf(10000000));
        String base64EncodedPhoto = "string";

        return new CreateSavingsGoalRequest(name, currency, target, base64EncodedPhoto);
    }

}
