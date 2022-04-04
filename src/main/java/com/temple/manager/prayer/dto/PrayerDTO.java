package com.temple.manager.prayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayerDTO {
    private long prayerId;

    private LocalDate prayerStartDate;

    private long believerId;

    private long prayerTypeCodeId;
}
