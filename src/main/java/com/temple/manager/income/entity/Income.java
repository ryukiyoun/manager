package com.temple.manager.income.entity;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.entity.Code;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INCOME_TYPE_CODE_ID", nullable = false)
    private Code code;

    private long cashAmount;

    private long cardAmount;

    private long bankBookAmount;

    private int installment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BELIEVER_ID")
    private Believer believer;

    @Column(nullable = false)
    private LocalDate incomeDate;

    @PostLoad
    public void postLoad() {
        if(this.believer == null)
            this.believer = Believer.builder().believerName("해심").build();
    }

    public void update(IncomeDTO incomeDTO){
        this.code = Code.builder().codeId(incomeDTO.getCode().getCodeId()).build();
        this.cashAmount = incomeDTO.getCashAmount();
        this.cardAmount = incomeDTO.getCardAmount();
        this.bankBookAmount = incomeDTO.getBankBookAmount();
        this.installment = incomeDTO.getInstallment();
        this.incomeDate = incomeDTO.getIncomeDate();

        if(incomeDTO.getBeliever() == null)
            this.believer = null;
    }
}
