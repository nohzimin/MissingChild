package com.mycom.myapp.domain.child.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycom.myapp.domain.child.dto.MissingChildDto;
import com.mycom.myapp.domain.child.dto.MissingChildRegisterDto;
import com.mycom.myapp.domain.child.dto.MissingChildResultDto;
import com.mycom.myapp.domain.child.dto.SearchImageRequest;
import com.mycom.myapp.domain.child.entity.MissingChild;
import com.mycom.myapp.domain.child.service.MissingChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/missing-child")
@RequiredArgsConstructor
public class MissingChildController {

    private final MissingChildService missingChildService;

//    @GetMapping("/list")
//    public Page<MissingChildDto> getAllMissingChild(@PageableDefault(size = 10, sort = "missingSince", direction = Sort.Direction.DESC) Pageable pageable) {
//        return missingChildService.getAllMissingChild(pageable);
//    }

    @GetMapping("/list")
    public Page<MissingChildDto> getAllMissingChild(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "oldest") String sort) {

        // 정렬 기준을 동적으로 처리
        Sort sorting;
        switch (sort) {
            case "name":
                sorting = Sort.by(Sort.Direction.ASC, "childName"); // 이름순
                break;
            case "recent":
                sorting = Sort.by(Sort.Direction.DESC, "missingSince"); // 최근 실종 순
                break;
            case "oldest":
                sorting = Sort.by(Sort.Direction.ASC, "missingSince"); // 오래된 순
                break;
            default:
                sorting = Sort.by(Sort.Direction.DESC, "createdAt"); // 기본값 (최신순)
        }

        // 페이지 정보와 정렬 기준을 포함한 Pageable 생성
        Pageable pageable = PageRequest.of(page, size, sorting);

        // 서비스 호출
        return missingChildService.getAllMissingChild(pageable);
    }



    @PostMapping("/search")
    public MissingChildResultDto searchMissingChild(@RequestParam(value = "name", required = false) String name,
                                                                   @RequestParam(value = "gender", required = false) Character gender,
                                                                   @RequestParam(value = "age", required = false) Integer age,
                                                                   @RequestParam(value = "location", required = false) String location,
                                                                   @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return missingChildService.searchMissingChild(name, gender, age, location, date);

    }

    @GetMapping("/recentList")
    public MissingChildResultDto searchRecent(){
        return missingChildService.searchRecent();
    }


    @PostMapping("/searchImage")
    public MissingChildResultDto searchMissingChildByImage(@RequestBody SearchImageRequest request) {
        List<String> classNames = request.getClassNames();
        List<MissingChildDto> missingChildDtoList = missingChildService.searchByClassNames(classNames);

        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();
        if (!missingChildDtoList.isEmpty()) {
            missingChildResultDto.setResult("success");
            missingChildResultDto.setMissingChildDtoList(missingChildDtoList);
        } else {
            missingChildResultDto.setResult("failure");
        }
        return missingChildResultDto;
    }

    @GetMapping("/list/{childId}")
    public MissingChildDto getMissingChildDetail(@PathVariable Integer childId) {
        return missingChildService.getMissingChildById(childId);
    }

    @PutMapping("/update/{childId}")
    public ResponseEntity<MissingChildDto> updateMissingChild(
            @PathVariable("childId") Integer childId,
            @RequestBody MissingChildDto missingChildDto) {
        return ResponseEntity.ok(missingChildService.updateMissingChild(childId, missingChildDto));
    }

    @DeleteMapping("/delete/{childId}")
    public ResponseEntity<Void> deleteMissingChild(@PathVariable("childId") Integer childId) {
        missingChildService.deleteMissingChild(childId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<MissingChildRegisterDto> registerMissingChild(
            @RequestParam("missingChild") String missingChildJson,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam(value = "trainImages", required = false) List<MultipartFile> trainImages) throws JsonProcessingException {

        // JSON 문자열을 DTO로 변환
        // ObjectMapper에 JavaTimeModule 등록 // LocalDateTime 직렬화-역직렬화 문제 해결을 위함
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MissingChildDto missingChildDto = objectMapper.readValue(missingChildJson, MissingChildDto.class);

        // 서비스 호출
        MissingChildRegisterDto responseDto = missingChildService.saveMissingChildWithImages(missingChildDto, mainImage, trainImages);

        return ResponseEntity.ok(responseDto);
    }


}
