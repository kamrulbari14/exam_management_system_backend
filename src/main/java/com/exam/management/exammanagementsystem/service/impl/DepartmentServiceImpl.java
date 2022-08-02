package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.DepartmentDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.entity.Admin;
import com.exam.management.exammanagementsystem.entity.Department;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.DepartmentRepository;
import com.exam.management.exammanagementsystem.service.DepartmentService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final String root = "Department";

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Response saveDepartment(DepartmentDto departmentDto) {

        if (departmentRepository.findByDepartmentAndActiveStatus(departmentDto.getDepartment(), ActiveStatus.ACTIVE.getValue()).isPresent()) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, root + " mail already exists");
        }

        Department department = modelMapper.map(departmentDto, Department.class);
        departmentRepository.save(department);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
    }

    @Override
    public List<DepartmentDto> getDepartmentList() {
        List<Department> departments = departmentRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (departments.isEmpty()) {
            return null;
        }
        return getDepartmentList(departments);
    }

    private List<DepartmentDto> getDepartmentList(List<Department> departments) {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        departments.forEach(department -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
            departmentDtos.add(departmentDto);
        });
        return departmentDtos;
    }
}
