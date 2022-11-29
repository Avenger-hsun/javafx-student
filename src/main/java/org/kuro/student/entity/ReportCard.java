package org.kuro.student.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCard {

    private String id;
    private String student_id;
    private String student_no;
    private String student_name;
    // 科目1
    private String subject1;
    // 科目2
    private String subject2;

    private Integer status;
    private Date create_time;
    private Date update_time;
}
