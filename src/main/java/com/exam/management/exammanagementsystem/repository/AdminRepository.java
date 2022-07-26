package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailAndActiveStatus(String name, Integer status);

    Optional<Admin> findByIdAndActiveStatus(Long id, Integer status);

    List<Admin> findAllByActiveStatus(Integer status);
}
