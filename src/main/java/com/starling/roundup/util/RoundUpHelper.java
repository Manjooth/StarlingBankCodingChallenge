package com.starling.roundup.util;

import com.starling.roundup.components.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class RoundUpHelper
{

    public BigDecimal roundUpTransactionAmount(final List<Transaction> transactions)
    {
        BigDecimal rounding = BigDecimal.ZERO;

        for(Transaction transaction : transactions)
        {
            final BigDecimal amount = transaction.getBalance().getMinorUnits();

            if(!amount.equals(BigDecimal.ZERO) && transaction.getDirection().equals("OUT"))
            {
                rounding = rounding.add(this.round(transaction));
            }
        }

        return rounding;
    }

    private BigDecimal round(final Transaction transaction)
    {
        final BigDecimal amount = transaction.getBalance().getMinorUnits();
        final BigDecimal oneHundred = new BigDecimal(100);
        final BigDecimal decimalAmount = amount.divide(oneHundred, 2, RoundingMode.UNNECESSARY);

        if(decimalAmount.equals(BigDecimal.ZERO))
        {
            return BigDecimal.ZERO;
        }

        final BigDecimal fractionalPart = decimalAmount.remainder(BigDecimal.ONE);
        return BigDecimal.ONE.subtract(fractionalPart);
    }

}
