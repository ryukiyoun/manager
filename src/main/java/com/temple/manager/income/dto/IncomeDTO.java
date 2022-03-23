package com.temple.manager.income.dto;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.enumable.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {
    private long incomeId;

    private PaymentType paymentType;

    private CodeDTO code;

    private long cashAmount;

    private long cardAmount;

    private long bankBookAmount;

    private int installment;

    private BelieverDTO believer;

    private LocalDate incomeDate;
}
