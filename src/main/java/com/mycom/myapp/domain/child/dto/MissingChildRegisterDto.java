package com.mycom.myapp.domain.child.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class MissingChildRegisterDto {
    private MissingChildDto missingChildDto;
    private List<ChildTrainImageDto> childTrainImageDtoList; // 리스트로 변경
}