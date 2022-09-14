package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SessionDto;

import java.util.List;

public interface SessionService {
    Response saveSession(SessionDto sessionDto);

    List<SessionDto> getSessionList();

}
