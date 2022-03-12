package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {
    private Long id;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updateAt;
    private Integer activeStatus;

}
