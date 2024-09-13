package com.mycom.myapp.controller;

import com.mycom.myapp.dto.MissingChildDto;
import com.mycom.myapp.dto.MissingChildRegisterDto;
import com.mycom.myapp.dto.MissingChildResultDto;
import com.mycom.myapp.dto.ReportDto;
import com.mycom.myapp.service.MissingChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/missing-child")
@RequiredArgsConstructor
public class MissingChildController {

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
