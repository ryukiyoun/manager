package com.temple.manager.prayer.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PrayerGridListDTO {
    private long prayerId;

    private LocalDate prayerStartDate;

    private long believerId;

    private String birthOfYear;

    private String believerName;

    private long codeId;

    private String prayerTypeCodeName;

    @QueryProjection

    public PrayerGridListDTO(long prayerId, LocalDate prayerStartDate, long believerId, String birthOfYear, String believerName, long codeId, String prayerTypeCodeName) {
        this.prayerId = prayerId;
        this.prayerStartDate = prayerStartDate;
        this.believerId = believerId;
        this.birthOfYear = birthOfYear;
        this.believerName = believerName;
        this.codeId = codeId;
        this.prayerTypeCodeName = prayerTypeCodeName;
    }
}
