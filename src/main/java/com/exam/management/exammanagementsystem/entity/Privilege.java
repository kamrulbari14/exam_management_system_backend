package com.exam.management.exammanagementsystem.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Privilege extends BaseEntity {
    private String name;
    private String endpoint;
    private String method;
}
