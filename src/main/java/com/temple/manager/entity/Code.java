package com.temple.manager.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
}
