package com.temple.manager.prayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayerTypeGroupCntDTO {
    private String prayerName;
    private long prayerCnt;
}