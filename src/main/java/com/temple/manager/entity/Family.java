package com.temple.manager.entity;

import com.temple.manager.converter.AesConverter;
import com.temple.manager.dto.FamilyDTO;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "active=99999999999999")
public class Family {
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

    @Column(nullable = false, length = 14)
    private String active;

    @PrePersist
    public void prePersist(){
        this.active = this.active == null ? "99999999999999" : this.active;
    }

    public void update(FamilyDTO familyDTO){
        this.familyType = familyDTO.getFamilyType();
        this.etcValue = familyDTO.getEtcValue();
        this.familyName = familyDTO.getFamilyName();
        this.birthOfYear = familyDTO.getBirthOfYear();
        this.lunarSolarType = familyDTO.getLunarSolarType();
    }

    public void delete(){
        this.active = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
