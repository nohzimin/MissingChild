package com.mycom.myapp.repository;

import com.mycom.myapp.entity.MissingChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingChildRegistrationRepository extends JpaRepository<MissingChild, Integer> {


}
