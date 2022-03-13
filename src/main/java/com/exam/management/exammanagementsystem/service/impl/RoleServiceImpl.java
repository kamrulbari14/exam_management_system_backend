package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.RoleDto;
import com.exam.management.exammanagementsystem.entity.Role;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.RoleRepository;
import com.exam.management.exammanagementsystem.service.RoleService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final String root = "Role info";

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response saveRole(RoleDto roleDto) {
        Role role;
        role = modelMapper.map(roleDto, Role.class);
        role = roleRepository.save(role);

        if (role != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public Response getRoles() {
        List<Role> roles = roleRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (roles.isEmpty() || roles == null) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Role list is empty");
        }
        List<RoleDto> roleDtoList = getRoleList(roles);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, root + "list retrieved", roleDtoList);
    }


    private List<RoleDto> getRoleList(List<Role> roles) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        roles.forEach(role -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            RoleDto roleDto = modelMapper.map(role, RoleDto.class);
            roleDtoList.add(roleDto);
        });
        return roleDtoList;
    }
}
