package org.kuro.student.mapper;

import org.apache.ibatis.annotations.Param;
import org.kuro.student.entity.LocalStudent;

import java.util.List;

public interface LocalStudentMapper {

    List<LocalStudent> selectAll();

    void save(LocalStudent student);

    void saveList(@Param("students") List<LocalStudent> students);
}
