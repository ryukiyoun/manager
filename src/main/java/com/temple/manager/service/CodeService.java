package com.temple.manager.service;

import com.temple.manager.dto.CodeDTO;
import com.temple.manager.entity.Code;
import com.temple.manager.mapper.CodeMapper;
import com.temple.manager.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeRepository codeRepository;

    public List<CodeDTO> getCodeByParentCodeValue(String parentCodeValue){
        List<Code> codeList = codeRepository.findAllByParentCodeValue(parentCodeValue);
        return CodeMapper.INSTANCE.entityListToDTOList(codeList);
    }
}
