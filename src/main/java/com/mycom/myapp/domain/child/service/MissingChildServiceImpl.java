package com.mycom.myapp.domain.child.service;

import com.mycom.myapp.domain.child.dto.ChildTrainImageDto;
import com.mycom.myapp.domain.child.dto.MissingChildDto;
import com.mycom.myapp.domain.child.dto.MissingChildRegisterDto;
import com.mycom.myapp.domain.child.dto.MissingChildResultDto;
import com.mycom.myapp.domain.child.entity.ChildTrainImage;
import com.mycom.myapp.domain.child.entity.MissingChild;
import com.mycom.myapp.domain.child.repository.ChildTrainImageRepository;
import com.mycom.myapp.domain.child.repository.MissingChildRepository;
import com.mycom.myapp.domain.user.entity.User;
import com.mycom.myapp.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MissingChildServiceImpl implements MissingChildService {

    private final MissingChildRepository missingChildRepository;
    private final UserRepository userRepository;
    private final ChildTrainImageRepository childTrainImageRepository;



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
            missingChildDto.setUserId(child.getUser().getUserId());


            missingChildDtoList.add(missingChildDto);
        });

        // 변환된 리스트를 MissingChildResultDto에 담기 + 결과 성공
        missingChildResultDto.setMissingChildDtoList(missingChildDtoList);
        missingChildResultDto.setResult("success");

        return missingChildResultDto;
    }

    // 모든 missingChild pagenation 해서 가져오기
    @Override
    public Page<MissingChildDto> getAllMissingChild(Pageable pageable) {
        Page<MissingChild> missingChildPage = missingChildRepository.findAll(pageable);

        return missingChildPage.map(child -> {
            MissingChildDto missingChildDto = new MissingChildDto();
            missingChildDto.setChildId(child.getChildId());
            missingChildDto.setChildName(child.getChildName());
            missingChildDto.setDateOfBirth(child.getDateOfBirth());
            missingChildDto.setChildGender(child.getChildGender());
            missingChildDto.setChildAge(child.getChildAge());
            missingChildDto.setLastKnownLocation(child.getLastKnownLocation());
            missingChildDto.setMissingSince(child.getMissingSince());
            missingChildDto.setPhotoUrl(child.getPhotoUrl());
            missingChildDto.setUserId(child.getUser().getUserId());
            return missingChildDto;
        });
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
            missingChildDto.setUserId(child.getUser().getUserId());


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
            missingChildDto.setUserId(child.getUser().getUserId());

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
                missingChildDto.setUserId(child.getUser().getUserId());


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
        missingChildDto.setUserId(missingChild.getUser().getUserId());

        return missingChildDto;
    }

//    @Override
//    @Transactional
//    public MissingChild saveMissingChild(MissingChildDto missingChildDto) {
//        User user = userRepository.findById(missingChildDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
//
//        MissingChild missingChild = new MissingChild();
//        missingChild.setChildName(missingChildDto.getChildName());
//        missingChild.setChildGender(missingChildDto.getChildGender());
//        missingChild.setDateOfBirth(missingChildDto.getDateOfBirth());
//        missingChild.setChildAge(missingChildDto.getChildAge());
//        missingChild.setLastKnownLocation(missingChildDto.getLastKnownLocation());
//        missingChild.setMissingSince(missingChildDto.getMissingSince());
//        missingChild.setPhotoUrl(missingChildDto.getPhotoUrl());
//        missingChild.setUser(user);
//
//        return missingChildRepository.save(missingChild);
//    }

    @Override
    @Transactional
    public MissingChildRegisterDto saveMissingChildWithImages(MissingChildRegisterDto missingChildRegisterDto) {
        MissingChildDto missingChildDto = missingChildRegisterDto.getMissingChildDto();
        List<ChildTrainImageDto> childTrainImageDtoList = missingChildRegisterDto.getChildTrainImageDtoList();

        // 사용자 정보 조회
        User user = userRepository.findById(missingChildDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // MissingChild 엔티티 설정
        MissingChild missingChild = new MissingChild();
        missingChild.setChildName(missingChildDto.getChildName());
        missingChild.setChildGender(missingChildDto.getChildGender());
        missingChild.setDateOfBirth(missingChildDto.getDateOfBirth());
        missingChild.setChildAge(missingChildDto.getChildAge());
        missingChild.setLastKnownLocation(missingChildDto.getLastKnownLocation());
        missingChild.setMissingSince(missingChildDto.getMissingSince());
        missingChild.setPhotoUrl(missingChildDto.getPhotoUrl());
        missingChild.setUser(user);

        // MissingChild 엔티티 저장
        MissingChild savedChild = missingChildRepository.save(missingChild);

        // 학습 이미지 리스트 저장
        if (childTrainImageDtoList != null && !childTrainImageDtoList.isEmpty()) {
            for (ChildTrainImageDto imageDto : childTrainImageDtoList) {
                ChildTrainImage trainImage = new ChildTrainImage();
                trainImage.setImageUrl(imageDto.getImageUrl());
                trainImage.setMissingChild(savedChild);
                ChildTrainImage savedImage = childTrainImageRepository.save(trainImage);
                imageDto.setImageId(savedImage.getImageId());
                imageDto.setChildId(savedChild.getChildId());
            }
        }

        // 반환할 DTO 설정
        missingChildDto.setChildId(savedChild.getChildId());
        MissingChildRegisterDto responseDto = new MissingChildRegisterDto();
        responseDto.setMissingChildDto(missingChildDto);
        responseDto.setChildTrainImageDtoList(childTrainImageDtoList);
        return responseDto;
    }


    @Override
    @Transactional
    public MissingChildDto updateMissingChild(Integer childId, MissingChildDto childDto) {
        MissingChild existingChild = missingChildRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Missing child not found with id: " + childId));

        updateChildFromDto(existingChild, childDto);
        MissingChild updatedChild = missingChildRepository.save(existingChild);
        return convertToMissingChildDto(updatedChild);
    }


    @Override
    @Transactional
    public void deleteMissingChild(Integer childId) {
        if (!missingChildRepository.existsById(childId)) {
            throw new RuntimeException("Missing child not found with id: " + childId);
        }
        missingChildRepository.deleteById(childId);
    }


    // Helper methods for DTO conversion
    private MissingChildDto convertToMissingChildDto(MissingChild child) {
        MissingChildDto dto = new MissingChildDto();
        dto.setChildId(child.getChildId());
        dto.setChildName(child.getChildName());
        dto.setChildGender(child.getChildGender());
        dto.setDateOfBirth(child.getDateOfBirth());
        dto.setChildAge(child.getChildAge());
        dto.setLastKnownLocation(child.getLastKnownLocation());
        dto.setMissingSince(child.getMissingSince());
        dto.setPhotoUrl(child.getPhotoUrl());
        dto.setUserId(child.getUser().getUserId());
        return dto;
    }

    private void updateChildFromDto(MissingChild child, MissingChildDto dto) {
        child.setChildName(dto.getChildName());
        child.setChildGender(dto.getChildGender());
        child.setDateOfBirth(dto.getDateOfBirth());
        child.setChildAge(dto.getChildAge());
        child.setLastKnownLocation(dto.getLastKnownLocation());
        child.setMissingSince(dto.getMissingSince());
        child.setPhotoUrl(dto.getPhotoUrl());
        child.setUpdatedAt(LocalDateTime.now());
        if (dto.getUserId() != null) {
            child.setUser(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId())));
        }
    }


}
