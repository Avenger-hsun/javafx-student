package org.kuro.student.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kuro.student.utils.WindowUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main extends Application {

    // Spring上下文
    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        // Spring配置文件路径
        String APPLICATION_CONTEXT_PATH = "/config/application-context.xml";
        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_PATH);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = applicationContext
                .getBean(SpringFXMLLoader.class)
                .getLoader("/layout/main/main-pane.fxml");
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("学生成绩管理系统");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setScene(scene);

        if (WindowUtils.isWindowsPlatform()) { // 如果是Windows平台
            // 去掉Windows自带的标题栏
            stage.initStyle(StageStyle.TRANSPARENT);
            // 为primaryStage添加自由缩放
            WindowUtils.addResizable(stage, 900, 580);
            // 为primaryStage添加一些GUI的修复代码
            WindowUtils.addWindowsStyle(stage);
        }

        // 添加最大化最小化的行为
        addMinimizeBehavior(stage);
        stage.show();

        // 获取屏幕可视化的宽高（Except TaskBar），把窗体设置在可视化的区域居中
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2.0);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2.0);
        // 为primaryStage添加Windows平台显示窗体时单击任务栏图标可以最小化
        WindowUtils.addWindowsPlatformTaskBarIconifyBehavior();
    }


    /**
     * 修补在最大化状态下，最小化窗体之后单击任务栏图标恢复时，窗体的高度和宽度是全屏的问题。
     * 修复后，宽度和高度是为屏幕可视化的宽度和高度
     *
     * @param stage Stage
     */
    private void addMinimizeBehavior(Stage stage) {
        stage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            // 确保Windows平台下，窗体在最大化状态下最小化后，单击任务栏图标显示时占据的屏幕大小是可视化的全屏
            if (stage.isMaximized() && WindowUtils.isWindowsPlatform()) {
                stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }
}
