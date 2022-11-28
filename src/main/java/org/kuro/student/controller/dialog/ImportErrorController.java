package org.kuro.student.controller.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.kuro.student.controller.student.LocalGradeController;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class ImportErrorController {

    // 确定按钮
    @FXML
    private Button btnConfirm;
    // 本地导入错误的成绩信息展示
    @FXML
    private ListView<String> errList;

    @Resource
    private LocalGradeController localGradeController;

    public void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList(localGradeController.errList);
        errList.setItems(list);
    }

    // 点击取消按钮
    @FXML
    public void onClickedConfirm(ActionEvent actionEvent) {
        // 隐藏窗口
        btnConfirm.getScene().getWindow().hide();
    }
}
