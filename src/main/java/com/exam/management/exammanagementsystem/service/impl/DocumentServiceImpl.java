package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.entity.BaseEntity;
import com.exam.management.exammanagementsystem.entity.Document;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.DocumentRepository;
import com.exam.management.exammanagementsystem.service.DocumentService;
import com.exam.management.exammanagementsystem.util.DateUtils;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    @Value("${file.root.location}")
    private String fileRootLocation;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    public Response saveDoc(String docName, MultipartFile file) {
        Document document = saveDocument(docName, file, Document.class, null);
        if (document != null) {
            document.setEntityRowId(document.getId());
            documentRepository.save(document);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public Document saveDocument(String docName, MultipartFile file, Class<? extends BaseEntity> modelClass, Long rowId) {
        if (file.isEmpty()) {
            return null;
        }
        String entityName = modelClass.getName();

        String fileName = file.getOriginalFilename();
        String location = getUniqueLocation(entityName, fileName);
        if (fileUpload(file, location)) {
            Document document = new Document();
            document.setDocType(docName);
            document.setDocName(fileName);
            document.setEntity(entityName);
            document.setDocLocation(location);
            document.setEntityRowId(rowId);
            return documentRepository.save(document);
        }

        return null;
    }

    @Override
    public Document findDocument(String classname, Long id) {
        Optional<Document> result = documentRepository.findByEntityAndEntityRowIdAndActiveStatus(classname, id,
                ActiveStatus.ACTIVE.getValue());
        return result.orElse(null);
    }

    private Boolean fileUpload(MultipartFile multipartFile, String location) {
        try {
            byte[] bytes = multipartFile.getBytes();
            String directionLocation = location.substring(0, location.lastIndexOf(File.separator));
            File file = new File(directionLocation);
            if (!file.exists()) {
                file.mkdirs();
            }
            Path path = Paths.get(directionLocation + File.separator + multipartFile.getOriginalFilename());
            Files.write(path, bytes);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String getUniqueLocation(String entityName, String fileName) {
        String location = fileRootLocation + entityName + File.separator + DateUtils.getStringDate(new Date(), "yyyy-MM-dd")
                + File.separator + UUID.randomUUID() + File.separator + fileName;
        int existingLocationCount = documentRepository.countByDocLocation(location);
        if (existingLocationCount == 0) {
            return location;
        }
        return getUniqueLocation(entityName, fileName);
    }
}
