package com.temple.manager.income.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.temple.manager.income.dto.IncomeGridListDTO;
import com.temple.manager.income.dto.QIncomeGridListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.temple.manager.believer.entity.QBeliever.believer;
import static com.temple.manager.code.entity.QCode.code;
import static com.temple.manager.income.entity.QIncome.income;

@Repository
@RequiredArgsConstructor
public class IncomeRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<IncomeGridListDTO> getIncomes(){
        return selectGridBaseQuery()
                .fetch();
    }

    public List<IncomeGridListDTO> getIncomeByBelieverId(long believerId){
        return selectGridBaseQuery()
                .where(income.believerId.eq(believerId))
                .fetch();
    }

    public List<IncomeGridListDTO> getIncomeTop5(Pageable pageable){
        return selectGridBaseQuery()
                .orderBy(income.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQueryBase<IncomeGridListDTO, JPAQuery<IncomeGridListDTO>> selectGridBaseQuery(){
        return queryFactory.select(
                new QIncomeGridListDTO(
                        income.incomeId,
                        income.paymentType,
                        code.codeId,
                        code.codeName,
                        income.cashAmount,
                        income.cardAmount,
                        income.bankBookAmount,
                        income.installment,
                        new CaseBuilder()
                                .when(believer.believerName.isNull()).then("해심")
                                .otherwise(believer.believerName).as("believerName"),
                        income.incomeDate
                ))
                .from(income)
                .innerJoin(code).on(income.incomeTypeCodeId.eq(code.codeId))
                .leftJoin(believer).on(income.believerId.eq(believer.believerId));
    }
}
