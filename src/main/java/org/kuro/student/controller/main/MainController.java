package org.kuro.student.controller.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.kuro.student.application.SpringFXMLLoader;
import org.kuro.student.utils.WindowUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class MainController {

    // 根容器stackPane
    @FXML
    private StackPane stackPane;

    // 根容器stackPane的第一个孩子容器borderPane，deep-index=0，表示在stackPane的第0层
    @FXML
    private BorderPane borderPane;

    public StackPane getStackPane() {
        return stackPane;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    // 注入Spring上下文对象
    @Resource
    private ApplicationContext applicationContext;

    public void initialize() throws IOException {
        // 如果是windows平台，加载自定义设计的标题栏、添加阴影效果
        if (WindowUtils.isWindowsPlatform()) {
            FXMLLoader fxmlLoader = applicationContext.getBean(SpringFXMLLoader.class)
                    .getLoader("/layout/main/main-top.fxml");
            borderPane.setTop(fxmlLoader.load());
            borderPane.getStyleClass().add("borderPaneDefault");
        }
    }
}
