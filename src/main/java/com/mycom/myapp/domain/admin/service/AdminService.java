package com.mycom.myapp.domain.admin.service;


import com.mycom.myapp.domain.admin.dto.AdminSignupRequestDto;
import com.mycom.myapp.domain.admin.dto.AdminSignupResultDto;

public interface AdminService {
    public Long getAdminId(String email);
    public AdminSignupResultDto signup(AdminSignupRequestDto adminSignupRequestDto);
}
