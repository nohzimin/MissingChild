package com.mycom.myapp.domain.child.repository;

import com.mycom.myapp.domain.child.entity.MissingChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingChildRegistrationRepository extends JpaRepository<MissingChild, Integer> {


}
