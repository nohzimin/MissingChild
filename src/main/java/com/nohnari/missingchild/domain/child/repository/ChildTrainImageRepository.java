package com.nohnari.missingchild.domain.child.repository;

import com.nohnari.missingchild.domain.child.entity.ChildTrainImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildTrainImageRepository  extends JpaRepository<ChildTrainImage, Long> {
}
