package com.exam.management.exammanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answers extends BaseEntity {
    private Double obtainedMark;

    private String givenAnswer;

    private String answer;

    private String status;

    @ManyToOne
    private Questions question;
}
