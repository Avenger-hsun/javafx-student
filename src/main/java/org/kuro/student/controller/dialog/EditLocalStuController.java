package org.kuro.student.controller.dialog;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.kuro.student.controller.student.LocalGradeController;
import org.kuro.student.entity.LocalStudent;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class EditLocalStuController {

    // 确定按钮
    @FXML
    public Button btnCancel;
    // 文本框
    @FXML
    public TextField noInput;
    @FXML
    public TextField nameInput;
    @FXML
    public ComboBox<String> courseInput;
    @FXML
    public TextField gradeInput;

    @Resource
    private LocalGradeController localGradeController;

    private LocalStudent clickedStudent;

    public void initialize() {
        clickedStudent = localGradeController.tableStudentLocal.getSelectionModel().getSelectedItem();
        if (clickedStudent == null) {
            return;
        }
        noInput.setText(clickedStudent.getNo());
        nameInput.setText(clickedStudent.getName());
        courseInput.setValue(clickedStudent.getCourse());
        gradeInput.setText(clickedStudent.getGrade());
    }

    // [修改]按钮点击事件
    @FXML
    public void onCreateButtonClicked(MouseEvent mouseEvent) {
        String noText = noInput.getText().trim();
        String nameText = nameInput.getText().trim();
        String courseText = courseInput.getValue().trim();
        String gradeText = gradeInput.getText().trim();

        if (StrUtil.isNotBlank(noText) && StrUtil.isNotBlank(nameText) &&
                StrUtil.isNotBlank(courseText) && StrUtil.isNotBlank(gradeText)) {
            LocalStudent student = new LocalStudent(
                    IdUtil.getSnowflakeNextIdStr(),
                    noText, nameText, courseText, gradeText
            );

            // 遍历students集合，找到点击项的索引（先删后加元素会在末尾插入）
            List<LocalStudent> list = localGradeController.students;
            int index = -1;
            for (int i = 0; i < list.size(); i++) {
                // if (clickedStudent.equals(list.get(i))) {
                //     index = i;
                // }
                if (clickedStudent.getId().equals(list.get(i).getId())) {
                    index = i;
                }
            }

            // 替换集合内对象，并刷新表格
            if (index != -1) {
                localGradeController.students.set(index, student);
                localGradeController.setTableData();
            }

            // 隐藏窗口
            btnCancel.getScene().getWindow().hide();
        }
    }


    // [取消]按钮点击事件
    @FXML
    public void onCancelButtonClicked(MouseEvent mouseEvent) {
        // 隐藏窗口
        btnCancel.getScene().getWindow().hide();
    }
}
