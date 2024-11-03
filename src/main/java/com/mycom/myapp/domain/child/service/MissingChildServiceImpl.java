package com.mycom.myapp.domain.child.service;

import com.mycom.myapp.domain.child.dto.MissingChildDto;
import com.mycom.myapp.domain.child.dto.MissingChildResultDto;
import com.mycom.myapp.domain.child.entity.MissingChild;
import com.mycom.myapp.domain.child.repository.MissingChildRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissingChildServiceImpl implements MissingChildService {

    private final MissingChildRepository missingChildRepository;
//    private final ReportRepository reportRepository;


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
    @Transactional
    public MissingChildDto getMissingChildById(Integer childId) {
        MissingChildDto missingChildDto = new MissingChildDto();
        MissingChild missingChild = missingChildRepository.findById(childId)
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디의 아동을 찾을 수 없습니다: " + childId));

        missingChildDto.setChildId(childId);
        missingChildDto.setChildName(missingChild.getChildName());
        missingChildDto.setChildGender(missingChild.getChildGender());
        missingChildDto.setDateOfBirth(missingChild.getDateOfBirth());
        missingChildDto.setChildAge(missingChild.getChildAge());
        missingChildDto.setLastKnownLocation(missingChild.getLastKnownLocation());
        missingChildDto.setMissingSince(missingChild.getMissingSince());
        missingChildDto.setPhotoUrl(missingChild.getPhotoUrl());

        // 필요시 User의 특정 정보 추가 설정 (예: User 이름)
//        missingChildDto.setUserName(missingChild.getUser().getName());


        return missingChildDto;

    }


//
//    @Override
//    @Transactional
//    public MissingChildResultDto insertMissingChild(MissingChildRegisterDto missingChildRegisterDto) {
//        MissingChildResultDto missingChildResultDto = new MissingChildResultDto();
//
//        try {
//            // Create and save the Report entity
//            Report report = new Report();
//            report.setReporterName(missingChildRegisterDto.getReporterName());
//            report.setReporterPhone(missingChildRegisterDto.getReporterPhone());
//            report.setReporterEmail(missingChildRegisterDto.getReporterEmail());
//            report.setReportDate(LocalDate.now()); // Set the current date
//
//            reportRepository.save(report);
//
//            // Create MissingChild entity and map values from DTO
//            MissingChild missingChild = new MissingChild();
//            missingChild.setChildName(missingChildRegisterDto.getChildName());
//            missingChild.setChildGender(missingChildRegisterDto.getChildGender());
//            missingChild.setChildAge(missingChildRegisterDto.getChildAge());
//            missingChild.setLastKnownLocation(missingChildRegisterDto.getLastKnownLocation());
//            missingChild.setMissingSince(missingChildRegisterDto.getMissingSince());
//            missingChild.setPhotoUrl(missingChildRegisterDto.getPhotoUrl()); // Set the uploaded image URL
//
//            // Associate the report with the missing child
//            missingChild.setReport(report); // If needed, link the report to the missing child
//
//            // Save the missing child entity
//            missingChildRepository.save(missingChild);
//
//            missingChildResultDto.setResult("success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            missingChildResultDto.setResult("fail: unexpected error");
//        }
//
//        return missingChildResultDto;
//    }



}
