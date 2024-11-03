package com.mycom.myapp.global.security.service;

import com.mycom.myapp.domain.admin.entity.Admin;
import com.mycom.myapp.domain.admin.repository.AdminRepository;
import com.mycom.myapp.global.security.dto.CustomAdminDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAdminDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(CustomAdminDetailService.class);
    public final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String adminEmail) throws UsernameNotFoundException {
        logger.info("adminEmail" + adminEmail);

        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        if (admin == null) {
            throw new UsernameNotFoundException("관리자를 찾을 수 없습니다.");
        }

        // Spring Security가 인식할 수 있는 User 객체를 반환
//        return new User(
//                admin.getAdminEmail(),
//                admin.getAdminPassword(),
//                getAuthorities(admin)
//        );
        return new CustomAdminDetails(admin);
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(Admin admin) {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return authorities;
//    }
}
