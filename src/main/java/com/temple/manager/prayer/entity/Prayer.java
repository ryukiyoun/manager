package com.temple.manager.prayer.entity;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.code.entity.Code;
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
    @JoinColumn(name = "PRAYER_TYPE_CODE_ID", nullable = false)
    private Code code;

    @Column(nullable = false, length = 14)
    String active;

    @PrePersist
    public void prePersist(){
        this.active = "99999999999999";
    }

    public void update(PrayerDTO prayerDTO){
        this.prayerStartDate = prayerDTO.getPrayerStartDate();
        this.believer = Believer.builder().believerId(prayerDTO.getBeliever().getBelieverId()).build();
        this.code = Code.builder().codeId(prayerDTO.getCode().getCodeId()).build();
    }

    public void delete(){
        this.active = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
