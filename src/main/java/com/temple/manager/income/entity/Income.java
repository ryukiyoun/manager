package com.temple.manager.income.entity;

import com.temple.manager.common.entity.BaseEntity;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.income.dto.IncomeDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update income set active = date_format(now(), '%Y%m%d%H%k%s') where income_id = ?")
@Where(clause = "active=99999999999999")
public class Income extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long incomeId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @JoinColumn(name = "INCOME_TYPE_CODE_ID", nullable = false, referencedColumnName = "CODE_ID")
    private long incomeTypeCodeId;

    private long cashAmount;

    private long cardAmount;

    private long bankBookAmount;

    private int installment;

    @JoinColumn(name = "BELIEVER_ID", referencedColumnName = "BELIEVER_ID")
    private Long believerId;

    @Column(nullable = false)
    private LocalDate incomeDate;

    public void update(IncomeDTO incomeDTO){
        this.incomeTypeCodeId = incomeDTO.getIncomeTypeCodeId();
        this.cashAmount = incomeDTO.getCashAmount();
        this.cardAmount = incomeDTO.getCardAmount();
        this.bankBookAmount = incomeDTO.getBankBookAmount();
        this.installment = incomeDTO.getInstallment();
        this.incomeDate = incomeDTO.getIncomeDate();
    }
}
