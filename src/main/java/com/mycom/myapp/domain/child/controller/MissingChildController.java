package com.mycom.myapp.domain.child.controller;

import com.mycom.myapp.domain.child.dto.MissingChildDto;
import com.mycom.myapp.domain.child.dto.MissingChildRegisterDto;
import com.mycom.myapp.domain.child.dto.MissingChildResultDto;
import com.mycom.myapp.domain.child.dto.SearchImageRequest;
import com.mycom.myapp.domain.child.entity.MissingChild;
import com.mycom.myapp.domain.child.service.MissingChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/missing-child")
@RequiredArgsConstructor
public class MissingChildController {

    // s3 예시 : https://velog.io/@leeeeeyeon/AWS-S3%EB%A1%9C-%ED%8C%8C%EC%9D%BC-%EC%84%9C%EB%B2%84%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EC%9E%90

    private final MissingChildService missingChildService;
//    private final ImageUploadService imageUploadService;

//    @GetMapping("/list")
//    public MissingChildResultDto getAllMissingChild(){
//        return missingChildService.getAllMissingChild();
//    }


    @GetMapping("/list")
    public Page<MissingChildDto> getAllMissingChild(@PageableDefault(size = 10, sort = "missingSince", direction = Sort.Direction.DESC) Pageable pageable) {
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
    public ResponseEntity<MissingChildRegisterDto> registerMissingChild(@RequestBody MissingChildRegisterDto missingChildRegisterDto) {
        MissingChildRegisterDto responseDto = missingChildService.saveMissingChildWithImages(missingChildRegisterDto);
        return ResponseEntity.ok(responseDto);
    }


}
