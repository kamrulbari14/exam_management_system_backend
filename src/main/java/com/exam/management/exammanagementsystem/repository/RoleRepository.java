package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String role);

    List<Role> findAllByActiveStatus(Integer activeStatus);
}
