package org.kuro.student.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalStudent {

    private String id;
    private String no;
    private String name;
    private String course;
    private String grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalStudent that = (LocalStudent) o;
        return id.equals(that.id) && no.equals(that.no) && name.equals(that.name) && course.equals(that.course) && grade.equals(that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, no, name, course, grade);
    }
}
