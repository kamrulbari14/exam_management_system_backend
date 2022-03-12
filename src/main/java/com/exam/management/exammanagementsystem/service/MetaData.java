package com.exam.management.exammanagementsystem.service;

import com.exam.management.exammanagementsystem.entity.Role;
import com.exam.management.exammanagementsystem.entity.User;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.RoleRepository;
import com.exam.management.exammanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class MetaData {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MetaData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void addUser() {
        Role superAdmin, role;
        String fullName = "Super Admin";
        String superman = "Superman";
        String email = "demomail@gmail.com";
        String adminRoleName = "ROLE_ADMIN";
        String superAdminRoleName = "ROLE_SUPER_ADMIN";
        String userRoleName = "ROLE_USER";
        role = roleRepository.findByRoleName(superAdminRoleName);

        if (role == null) {
            superAdmin = Role.builder().roleName(superAdminRoleName).description("SUPER ADMIN ROLE").build();
            role = roleRepository.save(superAdmin);
            Role admin = Role.builder().roleName(adminRoleName).description("ADMIN ROLE").build();
            roleRepository.save(admin);
            Role user = Role.builder().roleName(userRoleName).description("USER ROLE").build();
            roleRepository.save(user);
        }
        User user = userRepository.getByUserNameAndActiveStatusTrue(ActiveStatus.ACTIVE.getValue(), superman);
        if (user == null) {
            User superUser = User.builder()
                    .fullName(fullName)
                    .userName(superman)
                    .password(passwordEncoder.encode("Admin123"))
                    .email(email)
                    .roles(Arrays.asList(role))
                    .build();
            userRepository.save(superUser);
        }
    }
}
