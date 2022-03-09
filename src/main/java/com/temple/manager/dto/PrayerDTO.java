package com.temple.manager.dto;

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

    private BelieverDTO believer;

    private CodeDTO code;
}
