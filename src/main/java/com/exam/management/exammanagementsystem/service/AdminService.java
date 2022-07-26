package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.Response;

public interface AdminService {
    Response saveAdmin(AdminDto adminDto);

    Response getAdminList();

    Response isAdminOrNot(String email);

    Response deleteAdmin(Long id);
}
