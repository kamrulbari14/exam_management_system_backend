package com.exam.management.exammanagementsystem.service;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.entity.BaseEntity;
import com.exam.management.exammanagementsystem.entity.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Response saveDoc(String docName, MultipartFile file);

    Document saveDocument(String docName, MultipartFile file, Class<? extends BaseEntity> modelClass, Long rowId);

    Document findDocument(String classname, Long id);
}
