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
            FXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class)
                    .getLoader("/layout/dialog/delete-student.fxml");
            Stage primaryStage = ((Stage) localGradeController.tableStudentLocal.getScene().getWindow());
            Stage dialogStage = StageUtils.getStage((Stage) localGradeController.tableStudentLocal.getScene()
                    .getWindow(), loader.load());
            StageUtils.synchronizeCenter(primaryStage, dialogStage);
            // WindowUtils.blockBorderPane(mainController.getBorderPane());
            dialogStage.showAndWait();
        }
    }

    // 点击了编辑按钮
    @FXML
    public void onClickedEdit(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class)
                .getLoader("/layout/dialog/edit-student.fxml");
        Stage primaryStage = ((Stage) localGradeController.tableStudentLocal.getScene().getWindow());
        Stage dialogStage = StageUtils.getStage((Stage) localGradeController.tableStudentLocal.getScene()
                .getWindow(), loader.load());
        StageUtils.synchronizeCenter(primaryStage, dialogStage);
        dialogStage.showAndWait();
    }
}
