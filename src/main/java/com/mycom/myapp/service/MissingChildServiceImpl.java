package com.mycom.myapp.service;

import com.mycom.myapp.dto.MissingChildDto;
import com.mycom.myapp.dto.MissingChildRegisterDto;
import com.mycom.myapp.dto.MissingChildResultDto;
import com.mycom.myapp.dto.ReportDto;
import com.mycom.myapp.entity.MissingChild;
import com.mycom.myapp.entity.Report;
import com.mycom.myapp.repository.MissingChildRepository;
import com.mycom.myapp.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MissingChildServiceImpl implements MissingChildService {

    private final MissingChildRepository missingChildRepository;
    private final ReportRepository reportRepository;


    @Override
    public MissingChildResultDto getAllMissingChild() {
        // MissingChildResultDto 초기화
        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();

        // 모든 MissingChild 엔터티를 가져옴
        List<MissingChild> missingChildList = missingChildRepository.findAll();

        // missingChild entity 리스트를 MissingChildDto 변환 후 리스트에 담기
        List<MissingChildDto> missingChildDtoList = new ArrayList<>();
        missingChildList.forEach(child -> {
            MissingChildDto missingChildDto = new MissingChildDto();
            missingChildDto.setChildId(child.getChildId());
            missingChildDto.setChildName(child.getChildName());
            missingChildDto.setDateOfBirth(child.getDateOfBirth());
            missingChildDto.setChildGender(child.getChildGender());
            missingChildDto.setChildAge(child.getChildAge());
            missingChildDto.setLastKnownLocation(child.getLastKnownLocation());
            missingChildDto.setMissingSince(child.getMissingSince());
            missingChildDto.setPhotoUrl(child.getPhotoUrl());

            missingChildDtoList.add(missingChildDto);
        });

        // 변환된 리스트를 MissingChildResultDto에 담기 + 결과 성공
        missingChildResultDto.setMissingChildDtoList(missingChildDtoList);
        missingChildResultDto.setResult("success");

        return missingChildResultDto;
    }

    @Override
    public MissingChildResultDto searchMissingChild(String name, Character gender, Integer age, String location, LocalDate date) {
        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();

        // 이름 성별 나이 장소 날짜 로 검색
        List<MissingChild> missingChildList = missingChildRepository.searchMissingChild(name, gender, age, location, date);

        // missingChild entity 리스트를 MissingChildDto 변환 후 리스트에 담기
        List<MissingChildDto> missingChildDtoList = new ArrayList<>();
        missingChildList.forEach(child -> {
            MissingChildDto missingChildDto = new MissingChildDto();
            missingChildDto.setChildId(child.getChildId());
            missingChildDto.setChildName(child.getChildName());
            missingChildDto.setDateOfBirth(child.getDateOfBirth());
            missingChildDto.setChildGender(child.getChildGender());
            missingChildDto.setChildAge(child.getChildAge());
            missingChildDto.setLastKnownLocation(child.getLastKnownLocation());
            missingChildDto.setMissingSince(child.getMissingSince());
            missingChildDto.setPhotoUrl(child.getPhotoUrl());

            missingChildDtoList.add(missingChildDto);
        });

        // 변환된 리스트를 MissingChildResultDto에 담기 + 결과 성공
        missingChildResultDto.setMissingChildDtoList(missingChildDtoList);
        missingChildResultDto.setResult("success");

        return missingChildResultDto;
    }

    @Override
    public MissingChildResultDto searchRecent() {
        // MissingChildResultDto 초기화
        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();

        // 모든 MissingChild 엔터티를 가져옴
        List<MissingChild> missingChildList = missingChildRepository.searchRecent();

        // missingChild entity 리스트를 MissingChildDto 변환 후 리스트에 담기
        List<MissingChildDto> missingChildDtoList = new ArrayList<>();
        missingChildList.forEach(child -> {
            MissingChildDto missingChildDto = new MissingChildDto();
            missingChildDto.setChildId(child.getChildId());
            missingChildDto.setChildName(child.getChildName());
            missingChildDto.setDateOfBirth(child.getDateOfBirth());
            missingChildDto.setChildGender(child.getChildGender());
            missingChildDto.setChildAge(child.getChildAge());
            missingChildDto.setLastKnownLocation(child.getLastKnownLocation());
            missingChildDto.setMissingSince(child.getMissingSince());
            missingChildDto.setPhotoUrl(child.getPhotoUrl());

            missingChildDtoList.add(missingChildDto);
        });

        // 변환된 리스트를 MissingChildResultDto에 담기 + 결과 성공
        missingChildResultDto.setMissingChildDtoList(missingChildDtoList);
        missingChildResultDto.setResult("success");

        return missingChildResultDto;
    }


    @Override
    public  List<MissingChildDto> searchByClassNames(List<String> classNames) {
        List<MissingChildDto> missingChildDtoList = new ArrayList<>();

        for (String className : classNames) {
            // Fetching the MissingChild entities from the repository
            List<MissingChild> missingChildList = missingChildRepository.searchByClassName(className);

            // Converting each MissingChild entity to MissingChildDto
            missingChildList.forEach(child -> {
                MissingChildDto missingChildDto = new MissingChildDto();
                missingChildDto.setChildId(child.getChildId());
                missingChildDto.setChildName(child.getChildName());
                missingChildDto.setDateOfBirth(child.getDateOfBirth());
                missingChildDto.setChildGender(child.getChildGender());
                missingChildDto.setChildAge(child.getChildAge());
                missingChildDto.setLastKnownLocation(child.getLastKnownLocation());
                missingChildDto.setMissingSince(child.getMissingSince());
                missingChildDto.setPhotoUrl(child.getPhotoUrl());

                missingChildDtoList.add(missingChildDto); // Add the DTO to the results list
            });
        }

        return missingChildDtoList;
    }

    @Override
    public MissingChildResultDto insertReporter(ReportDto reportDto) {
        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();

        try{

            Report report = new Report();
            report.setReporterName(reportDto.getReporterName());
            report.setReporterPhone(reportDto.getReporterPhone());
            report.setReporterEmail(reportDto.getReporterEmail());
            report.setReportDate(LocalDate.now()); // 현재시각 넣기

            reportRepository.save(report);

            MissingChild missingChild = new MissingChild();
            missingChild.setReport(report);


            missingChildResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            missingChildResultDto.setResult("fail");
        }


        return missingChildResultDto;
    }



    @Override
    @Transactional
    public MissingChildResultDto insertMissingChild(MissingChildRegisterDto missingChildRegisterDto) {
        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();

        try{

            Report report = reportRepository.findById(missingChildRegisterDto.getRegisterId())
                    .orElseThrow(() -> new RuntimeException("Report not found"));

            // MissingChild entity 생성 및 Dto 값 매핑
            MissingChild missingChild = new MissingChild();
            missingChild.setChildName(missingChildRegisterDto.getChildName());
            missingChild.setChildGender(missingChildRegisterDto.getChildGender());
            missingChild.setDateOfBirth(missingChildRegisterDto.getDateOfBirth());
            missingChild.setChildAge(missingChildRegisterDto.getChildAge());
            missingChild.setLastKnownLocation(missingChildRegisterDto.getLastKnownLocation());
            missingChild.setMissingSince(missingChildRegisterDto.getMissingSince());
            missingChild.setPhotoUrl(missingChildRegisterDto.getPhotoUrl());

            missingChild.setReport(report);
            missingChildRepository.save(missingChild);

            missingChildResultDto.setResult("success");
        } catch (NoSuchElementException e) {
            missingChildResultDto.setResult("fail: report not found");
        } catch (Exception e) {
            e.printStackTrace();
            missingChildResultDto.setResult("fail: unexpected error");
        }

        return missingChildResultDto;
    }



}
