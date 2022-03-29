package com.temple.manager.believer.entity;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.common.entity.BaseEntity;
import com.temple.manager.converter.AesConverter;
import com.temple.manager.enumable.LunarSolarType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update believer set active = date_format(now(), '%Y%m%d%H%k%s') where believer_id = ?")
@Where(clause = "active=99999999999999")
public class Believer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long believerId;

    @Column(nullable = false, length = 100)
    private String believerName;

    @Column(nullable = false, length = 30)
    @Convert(converter = AesConverter.class)
    private String birthOfYear;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LunarSolarType lunarSolarType;

    @Column(nullable = false, length = 200)
    @Convert(converter = AesConverter.class)
    private String address;

    public void update(BelieverDTO believerDTO){
        this.believerName = believerDTO.getBelieverName();
        this.birthOfYear = believerDTO.getBirthOfYear();
        this.lunarSolarType = believerDTO.getLunarSolarType();
        this.address = believerDTO.getAddress();
    }
}
