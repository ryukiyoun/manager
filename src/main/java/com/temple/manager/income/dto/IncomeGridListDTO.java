package com.temple.manager.income.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.temple.manager.enumable.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class IncomeGridListDTO {
    private long incomeId;

    private PaymentType paymentType;

    private long codeId;

    private String codeName;

    private long cashAmount;

    private long cardAmount;

    private long bankBookAmount;

    private int installment;

    private String believerName;

    private LocalDate incomeDate;

    @QueryProjection
    public IncomeGridListDTO(long incomeId, PaymentType paymentType, long codeId, String codeName, long cashAmount, long cardAmount, long bankBookAmount, int installment, String believerName, LocalDate incomeDate) {
        this.incomeId = incomeId;
        this.paymentType = paymentType;
        this.codeId = codeId;
        this.codeName = codeName;
        this.cashAmount = cashAmount;
        this.cardAmount = cardAmount;
        this.bankBookAmount = bankBookAmount;
        this.installment = installment;
        this.believerName = believerName;
        this.incomeDate = incomeDate;
    }
}
