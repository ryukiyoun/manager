package com.temple.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeDTO {
    private long codeId;

    private String codeName;

    private String parentCodeValue;

    private String codeValue;

    private String att1;

    private String att2;

    private String att3;
}