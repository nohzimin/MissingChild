package com.mycom.myapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchImageRequest {
    private List<String> classNames;
}
