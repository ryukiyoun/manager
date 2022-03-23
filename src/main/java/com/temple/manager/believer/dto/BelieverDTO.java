package com.temple.manager.believer.dto;

import com.temple.manager.enumable.LunarSolarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BelieverDTO {
    private long believerId;

    private String believerName;

    private String birthOfYear;

    private LunarSolarType lunarSolarType;

    private String address;
}
