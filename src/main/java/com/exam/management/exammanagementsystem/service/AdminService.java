package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.Response;

import java.util.List;

public interface AdminService {
    Response saveAdmin(AdminDto adminDto);

    List<AdminDto> getAdminList();

    Boolean isAdminOrNot(String email);

    Response deleteAdmin(Long id);
}
