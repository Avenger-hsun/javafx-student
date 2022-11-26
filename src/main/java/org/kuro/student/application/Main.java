package org.kuro.student.application;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("学生管理系统");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.show();
    }
}
