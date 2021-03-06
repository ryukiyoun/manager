package com.temple.manager.code.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "99999999999999")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codeId;

    @Column(nullable = false, length = 30)
    private String codeName;

    @Column(nullable = false, length = 30)
    private String parentCodeValue;

    @Column(nullable = false, length = 30)
    private String codeValue;

    @Column(length = 30)
    private String att1;

    @Column(length = 30)
    private String att2;

    @Column(length = 30)
    private String att3;

    @Column(length = 14)
    private String active;
}
