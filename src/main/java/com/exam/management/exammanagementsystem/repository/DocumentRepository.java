package com.exam.management.exammanagementsystem.repository;


import com.exam.management.exammanagementsystem.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    int countByDocLocation(String location);

    Optional<Document> findByEntityAndEntityRowIdAndActiveStatus(String className, Long rowId, Integer id);
}
