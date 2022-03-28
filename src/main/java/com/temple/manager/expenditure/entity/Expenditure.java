package com.temple.manager.expenditure.entity;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.entity.Code;
import com.temple.manager.common.entity.BaseEntity;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.expenditure.dto.ExpenditureDTO;
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
@SQLDelete(sql = "update expenditure set active = date_format(now(), '%Y%m%d%H%k%s') where expenditure_id = ?")
@Where(clause = "active=99999999999999")
public class Expenditure extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long expenditureId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EXPENDITURE_TYPE_CODE_ID", nullable = false)
    private Code code;

    private long cashAmount;

    private long cardAmount;

    private long bankBookAmount;

    private int installment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BELIEVER_ID")
    private Believer believer;

    @Column(nullable = false)
    private LocalDate expenditureDate;

    @PostLoad
    public void postLoad(){
        if(this.believer == null)
            this.believer = Believer.builder().believerId(0).believerName("해심").build();
    }

    public void update(ExpenditureDTO expenditureDTO){
        this.code = Code.builder().codeId(expenditureDTO.getCode().getCodeId()).build();
        this.cashAmount = expenditureDTO.getCashAmount();
        this.cardAmount = expenditureDTO.getCardAmount();
        this.bankBookAmount = expenditureDTO.getBankBookAmount();
        this.installment = expenditureDTO.getInstallment();
        this.expenditureDate = expenditureDTO.getExpenditureDate();

        if(expenditureDTO.getBeliever() == null)
            this.believer = null;
    }
}
