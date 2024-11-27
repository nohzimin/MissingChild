package com.nohnari.missingchild.domain.child.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchImageRequest {
    private List<String> classNames;
}
