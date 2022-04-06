package com.temple.manager.user.entity;

import com.temple.manager.common.entity.BaseEntity;
import com.temple.manager.enumable.UserRole;
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
@SQLDelete(sql = "update user set active = date_format(now(), '%Y%m%d%H%k%s') where family_id = ?")
@Where(clause = "active=99999999999999")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false, length = 20)
    String userName;

    @Column(nullable = false, length = 100)
    String password;

    @Enumerated(value = EnumType.STRING)
    UserRole userRole;
}
