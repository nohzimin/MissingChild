package com.mycom.myapp.controller;

import com.mycom.myapp.dto.*;
import com.mycom.myapp.service.MissingChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/missing-child")
@RequiredArgsConstructor
public class MissingChildController {

    // s3 예시 : https://velog.io/@leeeeeyeon/AWS-S3%EB%A1%9C-%ED%8C%8C%EC%9D%BC-%EC%84%9C%EB%B2%84%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EC%9E%90

    private final MissingChildService missingChildService;

    @GetMapping("/list")
    public MissingChildResultDto getAllMissingChild(){
        return missingChildService.getAllMissingChild();
    }


    @PostMapping("/search")
    public MissingChildResultDto searchMissingChild(@RequestParam(value = "name", required = false) String name,
                                                                   @RequestParam(value = "gender", required = false) Character gender,
                                                                   @RequestParam(value = "age", required = false) Integer age,
                                                                   @RequestParam(value = "location", required = false) String location,
                                                                   @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return missingChildService.searchMissingChild(name, gender, age, location, date);

    }


    @PutMapping("/register/report")
    public ResponseEntity<MissingChildResultDto> insertReporter(@RequestBody ReportDto reportDto){
        MissingChildResultDto result = missingChildService.insertReporter(reportDto);

        if ("success".equals(result.getResult())) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }

    }



    @PutMapping("/register/child")
    public ResponseEntity<MissingChildResultDto> insertMissingChild(@RequestBody MissingChildRegisterDto missingChildRegisterDto){
        MissingChildResultDto result = missingChildService.insertMissingChild(missingChildRegisterDto);

        if ("success".equals(result.getResult())) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }

    }








}
