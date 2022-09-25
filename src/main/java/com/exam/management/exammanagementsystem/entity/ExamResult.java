package com.exam.management.exammanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answers> answers;

    @OneToOne
    private Exam exam;

    private String status;

    @OneToOne
    private Student student;
}
