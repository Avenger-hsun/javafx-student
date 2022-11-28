package org.kuro.student.controller.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.kuro.student.application.SpringFXMLLoader;
import org.kuro.student.utils.StageUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class TableActionController {

    // 注入Spring上下文类
    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private LocalGradeController localGradeController;

    // 点击了删除按钮
    @FXML
    public void onClickedDelete(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // 鼠标左击
            Stage stage =  ((Stage) localGradeController.tableStudentLocal.getScene().getWindow());;
            showDialog("/layout/dialog/delete-student-local-dialog.fxml", stage);
        }
    }

    // 点击了编辑按钮
    @FXML
    public void onClickedEdit(MouseEvent mouseEvent) throws Exception {
        Stage stage =  ((Stage) localGradeController.tableStudentLocal.getScene().getWindow());;
        showDialog("/layout/dialog/edit-student-dialog.fxml", stage);
    }


    // 显示弹框
    private void showDialog(String url, Stage stage) throws IOException {
        FXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class).getLoader(url);
        Stage dialogStage = StageUtils.getStage(stage, loader.load());
        StageUtils.synchronizeCenter(stage, dialogStage);
        dialogStage.showAndWait();
    }
}
