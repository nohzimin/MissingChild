package com.mycom.myapp.domain.child.repository;

import com.mycom.myapp.domain.child.entity.MissingChild;
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
                                            @Param("date") LocalDate date
    );


    //최근 등록된 6명 불러오기
    @Query("""
            select mc from MissingChild mc
            order by mc.childId desc limit 8
    """)
    List<MissingChild> searchRecent();





    // 이미지 검색
    @Query("select mc from MissingChild mc where mc.photoUrl LIKE %:className%")
    List<MissingChild> searchByClassName(@Param("className") String className);


}
