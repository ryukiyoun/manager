package com.temple.manager.expenditure.dto;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.dto.CodeDTO;
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
public class ExpenditureDTO {
    private long expenditureId;

    private PaymentType paymentType;

    private CodeDTO code;

    private long cashAmount;

    private long cardAmount;

    private long bankBookAmount;

    private int installment;

    private BelieverDTO believer;

    private LocalDate expenditureDate;
}
