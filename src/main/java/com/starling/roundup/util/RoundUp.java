package com.starling.roundup.util;

import com.starling.roundup.components.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class RoundUp {

    public BigDecimal roundUpTransactionAmount(List<Transaction> transactions){
        BigDecimal rounding = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            BigDecimal amount = transaction.getBalance().getMinorUnits();

            if(!amount.equals(BigDecimal.ZERO) && transaction.getDirection().equals("OUT")){
                rounding = rounding.add(this.round(transaction));
            }
        }
        System.out.println("Rounded amount: " + rounding);
        return rounding;
    }

    private BigDecimal round(Transaction transaction) {
        BigDecimal amount = transaction.getBalance().getMinorUnits();
        BigDecimal oneHundred = new BigDecimal(100);
        BigDecimal decimalAmount = amount.divide(oneHundred, 2, RoundingMode.UNNECESSARY);

        if(decimalAmount.equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO;
        }

        BigDecimal fractionalPart = decimalAmount.remainder(BigDecimal.ONE);

        System.out.println("minor units: " + transaction.getBalance().getMinorUnits() + " and divided: " + decimalAmount + " and fractional: " + fractionalPart + " roundup is: " + BigDecimal.ONE.subtract(fractionalPart));

        return BigDecimal.ONE.subtract(fractionalPart);
    }

}
