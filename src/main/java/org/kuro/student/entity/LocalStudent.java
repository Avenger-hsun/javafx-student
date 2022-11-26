package org.kuro.student.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalStudent {

    private String id;
    private String no;
    private String name;
    private String course;
    private String grade;
}
