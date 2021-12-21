package com.starling.roundup.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSavingsGoalRequest {

    private String name;
    private String currency;
    private Amount target;
    private String base64EncodedPhoto;

    public static CreateSavingsGoalRequest withDefaultValues() {

        final String name = "My savings goal " + new Random().nextInt(10000000);;
        final String currency = "GBP";
        final  Amount target = new Amount(currency, BigDecimal.valueOf(10000000));
        final String base64EncodedPhoto = "string";

        return new CreateSavingsGoalRequest(name, currency, target, base64EncodedPhoto);
    }

}
