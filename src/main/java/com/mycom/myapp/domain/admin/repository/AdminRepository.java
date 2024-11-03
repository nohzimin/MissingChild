package com.mycom.myapp.domain.admin.repository;

import com.mycom.myapp.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {

    Boolean existsByAdminEmail(String adminEmail);
    Admin findByAdminEmail(String adminEmail);
}
