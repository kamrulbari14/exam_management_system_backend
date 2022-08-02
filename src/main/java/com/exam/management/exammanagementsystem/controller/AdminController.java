package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiController
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/addAdmin")
    public Response saveAdmin(@RequestBody AdminDto adminDto){
        return adminService.saveAdmin(adminDto);
    }

    @PostMapping("/isAdmin")
    public Boolean isAdmin(@RequestBody AdminDto adminDto){
        return adminService.isAdminOrNot(adminDto.getEmail());
    }

    @GetMapping("/adminList")
    public List<AdminDto> getAdminList(){
        return adminService.getAdminList();
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public Response deleteAdmin(@PathVariable Long id){
        return adminService.deleteAdmin(id);
    }
}
