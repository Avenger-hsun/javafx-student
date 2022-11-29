package org.kuro.student.controller.student;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.kuro.student.entity.LocalStudent;
import org.kuro.student.entity.Student;
import org.kuro.student.mapper.StudentMapper;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Controller
public class StudentManageController {

    // 表格和列
    @FXML
    public TableView<Student> tableStudent;
    @FXML
    public TableColumn<Student, String> noColumn;
    @FXML
    public TableColumn<Student, String> nameColumn;
    @FXML
    public TableColumn<Student, String> genderColumn;
    @FXML
    public TableColumn<Student, String> facultyColumn;
    @FXML
    public TableColumn<Student, String> majorColumn;
    @FXML
    public TableColumn<Student, String> yearColumn;
    @FXML
    public TableColumn<Student, String> clsColumn;


    @FXML
    public void initialize() throws IOException {
        // 添加css名称.在CSS文件定制样式
        noColumn.getStyleClass().add("noColumn");
        nameColumn.getStyleClass().add("nameColumn");
        genderColumn.getStyleClass().add("genderColumn");
        facultyColumn.getStyleClass().add("facultyColumn");
        majorColumn.getStyleClass().add("majorColumn");
        yearColumn.getStyleClass().add("yearColumn");
        clsColumn.getStyleClass().add("clsColumn");

        noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        clsColumn.setCellValueFactory(new PropertyValueFactory<>("cls"));

        tableStudent.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tableStudent.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });

        // // 设置表格列的宽度随这个borderPane的宽度而动态改变
        tableStudent.widthProperty().addListener((observable, oldValue, newValue) -> {
            noColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.1);
            nameColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.1);
            genderColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 0.8);
            facultyColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.13);
            majorColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.1);
            yearColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 0.98);
            clsColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 0.98);
        });


        // 查询数据库获取学生列表，并展示到表格
        fetchStudents();
    }


    // 查询数据库获取学生列表，并展示到表格
    private void fetchStudents() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = factory.openSession();

        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.selectAll();
        session.commit();
        session.close();
        reader.close();

        if (students != null && students.size() > 0) {
            tableStudent.setItems(FXCollections.observableArrayList(students));
        }
    }
}
