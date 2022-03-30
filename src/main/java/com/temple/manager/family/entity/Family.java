package com.temple.manager.family.entity;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.common.entity.BaseEntity;
import com.temple.manager.converter.AesConverter;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.dto.FamilyDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update family set active = date_format(now(), '%Y%m%d%H%k%s') where family_id = ?")
@Where(clause = "active=99999999999999")
public class Family extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long familyId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FamilyType familyType;

    @Column(length = 50)
    private String etcValue;

    @Column(nullable = false, length = 100)
    @Convert(converter = AesConverter.class)
    private String familyName;

    @Column(nullable = false, length = 30)
    @Convert(converter = AesConverter.class)
    private String birthOfYear;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LunarSolarType lunarSolarType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BELIEVER_ID", nullable = false)
    private Believer believer;

    public void update(FamilyDTO familyDTO){
        this.familyType = familyDTO.getFamilyType();
        this.etcValue = familyDTO.getEtcValue();
        this.familyName = familyDTO.getFamilyName();
        this.birthOfYear = familyDTO.getBirthOfYear();
        this.lunarSolarType = familyDTO.getLunarSolarType();
    }
}
