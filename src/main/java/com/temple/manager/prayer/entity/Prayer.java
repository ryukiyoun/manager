package com.temple.manager.prayer.entity;

import com.temple.manager.common.entity.BaseEntity;
import com.temple.manager.prayer.dto.PrayerDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update prayer set active = date_format(now(), '%Y%m%d%H%k%s') where family_id = ?")
@Where(clause = "active=99999999999999")
public class Prayer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prayerId;

    @Column(nullable = false)
    private LocalDate prayerStartDate;

    @JoinColumn(name = "BELIEVER_ID", nullable = false, referencedColumnName = "BELIEVER_ID")
    private long believerId;

    @JoinColumn(name = "PRAYER_TYPE_CODE_ID", nullable = false, referencedColumnName = "CODE_ID")
    private long prayerTypeCodeId;

    public void update(PrayerDTO prayerDTO){
        this.prayerStartDate = prayerDTO.getPrayerStartDate();
        this.believerId = prayerDTO.getBelieverId();
        this.prayerTypeCodeId = prayerDTO.getPrayerTypeCodeId();
    }
}
