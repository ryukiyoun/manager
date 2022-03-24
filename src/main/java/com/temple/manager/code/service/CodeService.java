package com.temple.manager.code.service;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.mapper.CodeMapper;
import com.temple.manager.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;

    public List<CodeDTO> getCodeByParentCodeValue(String parentCodeValue){
        return codeMapper.entityListToDTOList(codeRepository.findAllByParentCodeValue(parentCodeValue));
    }
}
