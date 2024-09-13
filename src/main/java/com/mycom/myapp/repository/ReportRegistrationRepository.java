package com.mycom.myapp.repository;

import com.mycom.myapp.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRegistrationRepository extends JpaRepository<Report, Integer> {
}
