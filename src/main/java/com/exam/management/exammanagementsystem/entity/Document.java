package com.exam.management.exammanagementsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Document extends BaseEntity {
    private String docName;
    @Column(length = 3000)
    private String docLocation;
    private String docType;
    private String entity;
    private Long entityRowId;

}
