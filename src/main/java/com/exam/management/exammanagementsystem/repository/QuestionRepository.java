package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

}
