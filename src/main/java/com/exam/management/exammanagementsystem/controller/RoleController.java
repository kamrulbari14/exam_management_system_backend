package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.annotation.IsSuperAdmin;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.RoleDto;
import com.exam.management.exammanagementsystem.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.exam.management.exammanagementsystem.util.UrlConstraint.RoleManagement.GET_ROLES;
import static com.exam.management.exammanagementsystem.util.UrlConstraint.RoleManagement.SAVE_ROLE;


@ApiController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @IsSuperAdmin
    @PostMapping(SAVE_ROLE)
    public Response saveRole(@RequestBody RoleDto roleDto) {
        return roleService.saveRole(roleDto);
    }

    @IsSuperAdmin
    @GetMapping(GET_ROLES)
    public Response getRoles() {
        return roleService.getRoles();
    }
}
