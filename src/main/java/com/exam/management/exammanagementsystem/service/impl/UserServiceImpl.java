package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.UserDto;
import com.exam.management.exammanagementsystem.entity.Role;
import com.exam.management.exammanagementsystem.entity.User;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.RoleRepository;
import com.exam.management.exammanagementsystem.repository.UserRepository;
import com.exam.management.exammanagementsystem.service.UserService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exam.management.exammanagementsystem.util.AuthUtils.getLoggedInUsername;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final String root = "User info";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response saveUser(UserDto userDto) {
        User user = getByUserName(userDto.getUserName());
        if (user != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.IM_USED, "User Name " + userDto.getUserName() + " already in used", null);
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = modelMapper.map(userDto, User.class);
        user = userRepository.save(user);
        if (user != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserNameAndActiveStatusTrue(ActiveStatus.ACTIVE.getValue(), userName);
    }

    @Override
    public Response checkSuperAdmin() {
        boolean isSuperAdmin = true;
        List<Role> roles = userRepository.getByUserNameAndActiveStatusTrue(ActiveStatus.ACTIVE.getValue(), getLoggedInUsername()).getRoles()
                .stream().filter(authority -> authority.equals(roleRepository.findByRoleName("ROLE_SUPER_ADMIN"))).collect(Collectors.toList());
        if (roles.isEmpty()) {
            isSuperAdmin = false;
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "", isSuperAdmin);
    }
}
