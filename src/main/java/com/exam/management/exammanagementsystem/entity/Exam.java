package com.exam.management.exammanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam extends BaseEntity {

    private String examName;

    @OneToOne
    private Department department;

    @OneToOne
    private Session session;

    @OneToOne
    private Semester semester;

    private LocalDateTime time;

    private Integer duration;

    private Integer totalQuestion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Questions> question;

    private String mcqCategory;

    @OneToOne
    private Teacher teacherName;

    private String category;

    @ManyToMany
    private List<Student> students;
}
