package com.exam.management.exammanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questions extends BaseEntity {

    private String category;

    private Integer questionNumber;

    private String questionName;

    @OneToOne(cascade = CascadeType.ALL)
    private Mcq mcq;

    @OneToOne(cascade = CascadeType.ALL)
    private FillInTheGap fillInTheGap;

    private Double mark;

    private String assignmentDetails;

    private String assignmentCategory;

    private String fileCategory;

    private String vivaDetails;

    private String attendanceLink;

    private String hostLink;
}
