package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.DepartmentDto;
import com.exam.management.exammanagementsystem.dto.Response;

import java.util.List;

public interface DepartmentService {
    Response saveDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getDepartmentList();

}
