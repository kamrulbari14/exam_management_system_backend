package com.exam.management.exammanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

    private String name;
    private String roll;
    private String session;
    private String email;
    private String mobile;
    private String department;
    private String category;
}
