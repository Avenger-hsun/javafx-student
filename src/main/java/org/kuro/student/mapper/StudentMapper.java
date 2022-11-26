package org.kuro.student.mapper;

import org.kuro.student.entity.LocalStudent;

import java.util.List;

public interface StudentMapper {

    List<LocalStudent> selectAll();

    void saveList(List<LocalStudent> students);
}
