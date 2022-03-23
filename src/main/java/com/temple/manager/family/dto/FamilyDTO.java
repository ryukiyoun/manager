package com.temple.manager.family.dto;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDTO {
    private long familyId;

    private FamilyType familyType;

    private String etcValue;

    private String familyName;

    private String birthOfYear;

    private LunarSolarType lunarSolarType;

    private BelieverDTO believer;
}
