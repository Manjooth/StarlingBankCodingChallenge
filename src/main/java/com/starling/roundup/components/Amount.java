package com.starling.roundup.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount
{

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("minorUnits")
    private BigDecimal minorUnits;

}
