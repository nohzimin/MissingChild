package com.mycom.myapp.domain.child.service;

import com.mycom.myapp.domain.child.dto.MissingChildDto;
import com.mycom.myapp.domain.child.dto.MissingChildRegisterDto;
import com.mycom.myapp.domain.child.dto.MissingChildResultDto;
import com.mycom.myapp.domain.child.entity.MissingChild;

import java.time.LocalDate;
import java.util.List;

public interface MissingChildService {

    MissingChildResultDto getAllMissingChild();
    MissingChildResultDto searchMissingChild(String name, Character gender, Integer age, String location, LocalDate date);
    MissingChildResultDto searchRecent();
    List<MissingChildDto> searchByClassNames(List<String> classNames);
//    MissingChildResultDto insertMissingChild(MissingChildRegisterDto missingChildRegisterDto);
    MissingChildDto getMissingChildById(Integer childId);
}
