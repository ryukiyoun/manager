package com.temple.manager.entity;

import com.temple.manager.converter.AesConverter;
import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.enumable.LunarSolarType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Believer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long believerId;

    @Column(nullable = false, length = 100)
    private String believerName;

    @Column(nullable = false, length = 30)
    private String birthOfYear;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LunarSolarType lunarSolarType;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 14)
    private String active;

    public void update(BelieverDTO believerDTO){
        this.believerName = believerDTO.getBelieverName();
        this.birthOfYear = believerDTO.getBirthOfYear();
        this.lunarSolarType = believerDTO.getLunarSolarType();
        this.address = believerDTO.getAddress();
    }

    public void delete(){
        this.active = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
