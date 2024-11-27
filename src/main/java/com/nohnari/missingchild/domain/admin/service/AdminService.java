package com.nohnari.missingchild.domain.admin.service;


import com.nohnari.missingchild.domain.admin.dto.AdminSignupRequestDto;
import com.nohnari.missingchild.domain.admin.dto.AdminSignupResultDto;

public interface AdminService {
    public Long getAdminId(String email);
    public AdminSignupResultDto signup(AdminSignupRequestDto adminSignupRequestDto);
}
