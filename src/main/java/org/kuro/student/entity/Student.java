package org.kuro.student.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String id;
    private String no;
    private String name;
    private String gender;
    // 院系
    private String faculty;
    // 专业
    private String major;
    // 年级
    private String year;
    // 班级
    private String cls;
    private Integer status;
    private Date create_time;
    private Date update_time;

}
