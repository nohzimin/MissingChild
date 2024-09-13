package com.mycom.myapp.service;

import com.mycom.myapp.dto.MissingChildDto;
import com.mycom.myapp.dto.MissingChildRegisterDto;
import com.mycom.myapp.dto.MissingChildResultDto;
import com.mycom.myapp.dto.ReportDto;

import java.time.LocalDate;

public interface MissingChildService {

    MissingChildResultDto getAllMissingChild();
    MissingChildResultDto searchMissingChild(String name, Character gender, Integer age, String location, LocalDate date);

    MissingChildResultDto insertReporter(ReportDto reportDto);
    MissingChildResultDto insertMissingChild(MissingChildRegisterDto missingChildRegisterDto);
}
