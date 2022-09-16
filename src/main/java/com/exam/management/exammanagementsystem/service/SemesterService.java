package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SemesterDto;

import java.util.List;

public interface SemesterService {
    Response saveSemester(SemesterDto semesterDto);

    List<SemesterDto> getSemesterList();

    List<SemesterDto> getSemesterListByTeacherId(Long id);

    SemesterDto getSemesterById(Long id);

    Response updateSemester(SemesterDto semesterDto);

}
