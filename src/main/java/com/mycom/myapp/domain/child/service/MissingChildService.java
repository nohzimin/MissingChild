package com.mycom.myapp.domain.child.service;

import com.mycom.myapp.domain.child.dto.MissingChildDto;
import com.mycom.myapp.domain.child.dto.MissingChildRegisterDto;
import com.mycom.myapp.domain.child.dto.MissingChildResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface MissingChildService {

    MissingChildResultDto getAllMissingChild();
    Page<MissingChildDto> getAllMissingChild(Pageable pageable);
    MissingChildResultDto searchMissingChild(String name, Character gender, Integer age, String location, LocalDate date);
    MissingChildResultDto searchRecent();
    List<MissingChildDto> searchByClassNames(List<String> classNames);
    MissingChildDto getMissingChildById(Integer childId);
//    MissingChild saveMissingChild(MissingChildDto missingChildDto);
    MissingChildRegisterDto saveMissingChildWithImages(MissingChildDto missingChildDto, MultipartFile mainImage, List<MultipartFile> trainImages);
    MissingChildDto updateMissingChild(Integer childId, MissingChildDto childDto);
    void deleteMissingChild(Integer childId);

}
