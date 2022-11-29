package org.kuro.student.mapper;

import org.kuro.student.entity.Student;

import java.util.List;

public interface StudentMapper {

    List<Student> selectAll();
}
