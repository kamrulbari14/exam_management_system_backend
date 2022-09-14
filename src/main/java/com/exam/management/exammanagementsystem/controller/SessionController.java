package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SessionDto;
import com.exam.management.exammanagementsystem.service.SessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiController
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/addSession")
    public Response saveSession(@RequestBody SessionDto sessionDto) {
        return sessionService.saveSession(sessionDto);
    }

    @GetMapping("/sessions")
    public List<SessionDto> getSessionList() {
        return sessionService.getSessionList();
    }

}
