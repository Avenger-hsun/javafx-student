package org.kuro.student.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.kuro.student.controller.student.LocalGradeController;
import org.kuro.student.entity.LocalStudent;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class DelLocalStuController {

    // 取消按钮
    @FXML
    public Button btnCancel;
    // 确认文字
    @FXML
    public Label confirmText;

    @Resource
    private LocalGradeController localGradeController;

    public void initialize() {
        // 获取当前选择的行数据
        LocalStudent item = localGradeController.tableStudentLocal.getSelectionModel().getSelectedItem();
        if (item == null) {
            confirmText.setText("请先选中您要删除的学生！");
            return;
        }
        confirmText.setText("确定删除【" + item.getName() + "】吗？");
    }

    // 点击确定按钮
    @FXML
    public void onClickedConfirm(ActionEvent actionEvent) {
        // 获取选中的行，从students集合中删除该数据，并刷新表格
        LocalStudent item = localGradeController.tableStudentLocal.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        localGradeController.students.remove(item);
        localGradeController.setTableData();

        // 隐藏窗口
        btnCancel.getScene().getWindow().hide();
    }

    // 点击取消按钮
    @FXML
    public void onClickedCancel(ActionEvent actionEvent) {
        // 隐藏窗口
        btnCancel.getScene().getWindow().hide();
    }
}
