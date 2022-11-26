package org.kuro.student.controller.main;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class CenterController {

    /**
     * 中间的StackPane容器
     */
    @FXML
    private StackPane stackPane;

    /**
     * 中间StackPane第0层的borderPane，StackPane的层数如 0,1,2...
     */
    @FXML
    private BorderPane borderPane;

    @Resource
    private LeftController leftController;

    @Resource
    private MainController mainController;

    public StackPane getStackPane() {
        return stackPane;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
