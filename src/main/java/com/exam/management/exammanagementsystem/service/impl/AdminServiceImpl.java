package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.entity.Admin;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.AdminRepository;
import com.exam.management.exammanagementsystem.service.AdminService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final String root = "Admin";

    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Response saveAdmin(AdminDto adminDto) {

        if (adminRepository.findByEmailAndActiveStatus(adminDto.getEmail(), ActiveStatus.ACTIVE.getValue()).isPresent()) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, root + " mail already exists");
        }

        Admin admin = modelMapper.map(adminDto, Admin.class);
        adminRepository.save(admin);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
    }

    @Override
    public List<AdminDto> getAdminList() {
        List<Admin> admins = adminRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (admins.isEmpty()) {
            return null;
        }
        return getAdminList(admins);
    }

    @Override
    public Boolean isAdminOrNot(String email) {
        return adminRepository.findByEmailAndActiveStatus(email, ActiveStatus.ACTIVE.getValue()).isPresent();
    }

    @Override
    public Response deleteAdmin(Long id) {
        Optional<Admin> result = adminRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());

        if (result.isPresent()) {
            Admin admin = result.get();
            admin.setActiveStatus(ActiveStatus.DELETE.getValue());
            adminRepository.save(admin);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, root + " deleted successfully!", null);
        }

        return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, root + " not found");
    }


    private List<AdminDto> getAdminList(List<Admin> admins) {
        List<AdminDto> adminDtoList = new ArrayList<>();
        admins.forEach(admin -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            AdminDto adminDto = modelMapper.map(admin, AdminDto.class);
            adminDtoList.add(adminDto);
        });
        return adminDtoList;
    }
}
