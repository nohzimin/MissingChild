package com.mycom.myapp.domain.child.repository;

import com.mycom.myapp.domain.child.entity.ChildTrainImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildTrainImageRepository  extends JpaRepository<ChildTrainImage, Long> {
}
