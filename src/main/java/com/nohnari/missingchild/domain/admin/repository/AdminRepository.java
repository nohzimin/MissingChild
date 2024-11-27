package com.nohnari.missingchild.domain.admin.repository;

import com.nohnari.missingchild.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {

    Boolean existsByAdminEmail(String adminEmail);
    Admin findByAdminEmail(String adminEmail);
    Admin findByAdminId(Long adminId);
}
