package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.RoleDto;

public interface RoleService {
    Response saveRole(RoleDto roleDto);

    Response getRoles();
}
