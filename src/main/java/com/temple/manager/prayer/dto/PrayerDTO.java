package com.temple.manager.prayer.dto;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.dto.CodeDTO;
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
