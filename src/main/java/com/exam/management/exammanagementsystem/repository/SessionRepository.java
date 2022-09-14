package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByDepartmentAndSessionAndActiveStatus(String department, String session, Integer status);

    Optional<Session> findByIdAndActiveStatus(Long id, Integer status);

    List<Session> findAllByActiveStatus(Integer status);
}
