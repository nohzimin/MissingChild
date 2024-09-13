package com.mycom.myapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class MissingChildResultDto {
    private String result;
    private MissingChildDto missingChildDto;
    private List<MissingChildDto> missingChildDtoList;
    private ReportDto reportDto;
    private SearchDto searchDto;
    private List<SearchDto> searchDtoList;
}
