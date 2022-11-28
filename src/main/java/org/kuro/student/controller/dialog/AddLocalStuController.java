package org.kuro.student.controller.dialog;

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

@Controller
public class AddLocalStuController {

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

    // [添加]按钮点击事件
    @FXML
    public void onCreateButtonClicked(MouseEvent mouseEvent) {
        String noText = noInput.getText().trim();
        String nameText = nameInput.getText().trim();
        String courseText = courseInput.getValue().trim();
        String gradeText = gradeInput.getText().trim();

        if (StrUtil.isNotBlank(noText) && StrUtil.isNotBlank(nameText) &&
                StrUtil.isNotBlank(courseText) && StrUtil.isNotBlank(gradeText)) {
            LocalStudent student = new LocalStudent(noText, nameText, courseText, gradeText);
            // 添加学生数据并刷新表格
            localGradeController.students.add(student);
            localGradeController.setTableData();

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
