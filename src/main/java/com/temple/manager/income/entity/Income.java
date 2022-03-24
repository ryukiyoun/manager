package com.temple.manager.income.entity;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.entity.Code;
import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.enumable.PaymentType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "active=99999999999999")
public class Income {
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

    @Column(nullable = false)
    String active;

    @PrePersist
    public void prePersist(){
        this.active = "99999999999999";
    }

    public void update(IncomeDTO incomeDTO){
        this.code = Code.builder().codeId(incomeDTO.getCode().getCodeId()).build();
        this.cashAmount = incomeDTO.getCashAmount();
        this.cardAmount = incomeDTO.getCardAmount();
        this.bankBookAmount = incomeDTO.getBankBookAmount();
        this.installment = incomeDTO.getInstallment();
        this.incomeDate = incomeDTO.getIncomeDate();
    }

    public void delete(){
        this.active = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
