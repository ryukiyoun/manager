package com.temple.manager.expenditure.entity;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.entity.Code;
import com.temple.manager.expenditure.dto.ExpenditureDTO;
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
public class Expenditure {
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

    @Column(nullable = false)
    String active;

    @PrePersist
    public void prePersist(){
        this.active = this.active == null ? "99999999999999" : this.active;
    }

    public void update(ExpenditureDTO expenditureDTO){
        this.code = Code.builder().codeId(expenditureDTO.getCode().getCodeId()).build();
        this.cashAmount = expenditureDTO.getCashAmount();
        this.cardAmount = expenditureDTO.getCardAmount();
        this.bankBookAmount = expenditureDTO.getBankBookAmount();
        this.installment = expenditureDTO.getInstallment();
        this.expenditureDate = expenditureDTO.getExpenditureDate();
    }

    public void delete(){
        this.active = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
