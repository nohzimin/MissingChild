package com.mycom.myapp.repository;

import com.mycom.myapp.dto.MissingChildDto;
import com.mycom.myapp.entity.MissingChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissingChildRepository extends JpaRepository<MissingChild, Integer> {
    // 실종 아동 이름, 성별, 나이, 조회
    @Query("""
            select mc from MissingChild mc
             where (:name IS NULL OR mc.childName LIKE %:name%) 
               and (:gender IS NULL OR mc.childGender = :gender) 
               and (:age IS NULL OR mc.childAge = :age)
               and (:location IS NULL OR mc.lastKnownLocation LIKE %:location%)
               and (:date IS NULL OR mc.missingSince = :date)
    """)
    List<MissingChild> searchMissingChild( @Param("name") String name,
                                        @Param("gender") Character gender,
                                        @Param("age") Integer age,
                                        @Param("location") String location,
                                        @Param("date") LocalDate date );
}
