package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SessionDto;
import com.exam.management.exammanagementsystem.entity.Session;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.SessionRepository;
import com.exam.management.exammanagementsystem.service.SessionService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;
    private final String root = "Session";

    public SessionServiceImpl(SessionRepository sessionRepository, ModelMapper modelMapper) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response saveSession(SessionDto sessionDto) {

        if (sessionRepository.findByDepartmentAndSessionAndActiveStatus(sessionDto.getDepartment(), sessionDto.getSession(),
                ActiveStatus.ACTIVE.getValue()).isPresent()) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, root + " session already exists under this department");
        }

        Session session = modelMapper.map(sessionDto, Session.class);
        sessionRepository.save(session);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
    }

    @Override
    public List<SessionDto> getSessionList() {
        List<Session> sessions = sessionRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (sessions.isEmpty()) {
            return null;
        }
        return getSessionList(sessions);
    }

    private List<SessionDto> getSessionList(List<Session> sessions) {
        List<SessionDto> sessionDtos = new ArrayList<>();
        sessions.forEach(session -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            SessionDto sessionDto = modelMapper.map(session, SessionDto.class);
            sessionDtos.add(sessionDto);
        });
        return sessionDtos;
    }
}
