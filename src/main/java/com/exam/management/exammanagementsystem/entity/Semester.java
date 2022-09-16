package com.exam.management.exammanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Semester extends BaseEntity {

    private String semester;
    private String department;
    private String session;
    @OneToMany
    private List<Teacher> teacher;
}
