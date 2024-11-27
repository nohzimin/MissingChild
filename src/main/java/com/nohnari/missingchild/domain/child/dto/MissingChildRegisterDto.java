package com.nohnari.missingchild.domain.child.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MissingChildRegisterDto {
    private MissingChildDto missingChildDto;
    private List<ChildTrainImageDto> childTrainImageDtoList; // 리스트로 변경
}