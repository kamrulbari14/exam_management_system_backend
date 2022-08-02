package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.entity.Document;
import com.exam.management.exammanagementsystem.service.DocumentService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@ApiController
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(value = "/get-file/{className}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] getFile(HttpServletResponse response,
                          @PathVariable String className, @PathVariable Long id) throws IOException {
        Document document = documentService.findDocument(className, id);
        File downloadableFile = new File(document.getDocLocation());
        InputStream in = new FileInputStream(downloadableFile);
        response.setHeader("Content-Disposition", "attachment; filename="+ document.getDocName().replaceAll("[^a-zA-Z0-9.]", ""));
        return IOUtils.toByteArray(in);
    }
}
