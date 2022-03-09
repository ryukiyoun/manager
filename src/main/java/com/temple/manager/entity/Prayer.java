package com.temple.manager.entity;

import com.temple.manager.dto.PrayerDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Prayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prayerId;

    @Column(nullable = false)
    private LocalDate prayerStartDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BELIEVER_ID", nullable = false)
    private Believer believer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CODE_ID", nullable = false)
    private Code code;

    @Column(nullable = false, length = 14)
    String active;

    public void update(PrayerDTO prayerDTO){
        this.prayerStartDate = prayerDTO.getPrayerStartDate();
        this.believer = Believer.builder().believerId(prayerDTO.getBeliever().getBelieverId()).build();
        this.code = Code.builder().codeId(prayerDTO.getCode().getCodeId()).build();
    }

    public void delete(){
        this.active = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
