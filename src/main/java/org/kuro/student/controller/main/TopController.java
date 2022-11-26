package org.kuro.student.controller.main;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class TopController {

    // 标题栏的最小化图片组件
    @FXML
    private ImageView ivMinimize;

    // 标题栏的最大化图片组件
    @FXML
    private ImageView ivMaximize;

    // 标题栏的退出图片组件
    @FXML
    private ImageView ivExit;

    // 包裹标题文字和最小化、最大化、关闭/退出按钮的BorderPane
    @FXML
    private BorderPane titleBar;

    public void initialize() {
        ivMinimize.setCursor(Cursor.DEFAULT);
        ivMaximize.setCursor(Cursor.DEFAULT);
        ivExit.setCursor(Cursor.DEFAULT);
        titleBar.setCursor(Cursor.DEFAULT);
    }


    // 最小化Label按钮事件处理
    @FXML
    public void onClickedMinimize(MouseEvent mouseEvent) { // 最小化按钮鼠标单击事件
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // 如果按下鼠标左键，最小化primaryStage
            // 窗体primaryStage对象
            Stage primaryStage = (Stage) ivMinimize.getParent().getScene().getWindow();
            ivMinimize.setImage(new Image("/images/MinimizeDefault.png"));
            primaryStage.setIconified(true);
        }
    }

    // 最小化按钮鼠标经过事件
    @FXML
    public void onEnteredMinimize(MouseEvent mouseEvent) {
        if (ivMinimize.getCursor() == Cursor.DEFAULT) {
            ivMinimize.setImage(new Image("/images/Minimize.png"));
        }
    }

    // 最小化按钮鼠标移除事件
    @FXML
    public void onExitedMinimize(MouseEvent mouseEvent) {
        if (ivMinimize.getCursor() == Cursor.DEFAULT) {
            ivMinimize.setImage(new Image("/images/MinimizeDefault.png"));
        }
    }

    // 最大化Label按钮事件处理
    @FXML
    public void onClickedMaximize(MouseEvent mouseEvent) {
        // 如果按下鼠标左键，最大化/最小化primaryStage
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // 获取窗体primaryStage对象
            Stage primaryStage = (Stage) ivMaximize.getParent().getScene().getWindow();

            // 如果primaryStage是最小化，设置成最大化
            if (!primaryStage.isMaximized()) {
                ivMaximize.setImage(new Image("/images/MaximizedDefault.png"));
                // 设置primaryStage高度、宽度为屏幕的可视化高度、宽度（不包括Windows底下的任务栏）
                primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                primaryStage.setMaximized(true);
            } else {  // 如果primaryStage不是最小化，设置成最小化
                primaryStage.setMaximized(false);
                ivMaximize.setImage(new Image("/images/MaximizeDefault.png"));
            }
        }
    }

    // 最大化按钮鼠标经过事件
    @FXML
    public void onEnteredMaximize(MouseEvent mouseEvent) {
        if (ivMaximize.getCursor() == Cursor.DEFAULT) {
            Stage primaryStage = (Stage) ivMaximize.getParent().getScene().getWindow();
            if (!primaryStage.isMaximized()) {
                ivMaximize.setImage(new Image("/images/Maximize.png"));
            } else {
                ivMaximize.setImage(new Image("/images/Maximized.png"));
            }
        }
    }

    //最大化按钮鼠标移除事件
    @FXML
    public void onExitedMaximize(MouseEvent mouseEvent) {
        if (ivMaximize.getCursor() == Cursor.DEFAULT) {
            Stage primaryStage = (Stage) ivMaximize.getParent().getScene().getWindow();
            if (!primaryStage.isMaximized()) {
                ivMaximize.setImage(new Image("/images/MaximizeDefault.png"));
            } else {
                ivMaximize.setImage(new Image("/images/MaximizedDefault.png"));
            }
        }
    }

    // 关闭/退出按钮事件处理
    @FXML
    public void onClickedExit(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {  // 如果按下鼠标左键，关闭primaryStage
            Stage primaryStage = (Stage) ivExit.getParent().getScene().getWindow();
            primaryStage.close();
        }
    }

    // 关闭/退出按钮鼠标经过事件
    @FXML
    public void onEnteredExit(MouseEvent mouseEvent) {
        if (ivExit.getCursor() == Cursor.DEFAULT) {
            ivExit.setImage(new Image("/images/Exit.png"));
        }
    }

    // 关闭/退出按钮鼠标移除事件
    @FXML
    public void onExitedExit(MouseEvent mouseEvent) {
        if (ivExit.getCursor() == Cursor.DEFAULT) {
            ivExit.setImage(new Image("/images/ExitDefault.png"));
        }
    }


    // TabBar拖拽事件(包裹标题文字和最小化、最大化、关闭/退出按钮的BorderPane)
    private double titleBarMousePressedX;  // 记录titleBar鼠标按下时的X坐标（即SceneX或X）
    private double titleBarMousePressedY;  // 记录titleBar鼠标按下时的Y坐标（即SceneY或Y）

    // BorderPane鼠标按下事件
    @FXML
    public void onTitleBarPressed(MouseEvent mouseEvent) {
        if (titleBar.getCursor() == Cursor.DEFAULT) {
            // 如果按下的位置不是最小化、最大化、关闭/退出按钮的范围，记录按下的X、Y坐标
            if (mouseEvent.getSceneX() < titleBar.getWidth() -
                    (ivMinimize.getFitWidth() + ivMaximize.getFitWidth() + ivExit.getFitWidth())
            ) {
                titleBarMousePressedX = mouseEvent.getX();
                titleBarMousePressedY = mouseEvent.getY();
            }
        }
    }

    // BorderPane鼠标拖拽事件
    @FXML
    public void onTitleBarDragged(MouseEvent mouseEvent) {
        if (titleBar.getCursor() == Cursor.DEFAULT) {
            if (!ivMinimize.isPressed() && !ivMaximize.isPressed() && !ivExit.isPressed()) {
                Stage primaryStage = (Stage) titleBar.getParent().getScene().getWindow();
                // 如果鼠标的屏幕位置ScreenX、Y在屏幕的可视化区域内，才执行移动窗体操作
                if (0 <= mouseEvent.getScreenX() &&
                        mouseEvent.getScreenX() <= Screen.getPrimary().getVisualBounds().getWidth() &&
                        0 <= mouseEvent.getScreenY() &&
                        mouseEvent.getScreenY() <= Screen.getPrimary().getVisualBounds().getHeight()
                ) {
                    if (primaryStage.isMaximized()) {  // 如果是最大化状态下拖拽，变为未最大化的状态
                        // 记录计算按下鼠标时的百分比(Y坐标不需要计算，因为Y坐标本身没有变化)
                        double validTitleBarWidth = primaryStage.getWidth() - ivMinimize.getFitWidth() - ivMaximize.getFitWidth() - ivExit.getFitWidth();
                        double percentageX = titleBarMousePressedX / validTitleBarWidth;
                        // 设置成未最大化的状态
                        primaryStage.setMaximized(false);
                        ivMaximize.setImage(new Image("/images/MaximizeDefault.png"));
                        // 重新计算未最大化的状态的鼠标按下坐标
                        validTitleBarWidth = primaryStage.getWidth() - ivMinimize.getFitWidth() - ivMaximize.getFitWidth() - ivExit.getFitWidth();
                        titleBarMousePressedX = validTitleBarWidth * percentageX;
                    }
                    // 更新主舞台的坐标
                    primaryStage.setX(mouseEvent.getScreenX() - titleBarMousePressedX);
                    primaryStage.setY(mouseEvent.getScreenY() - titleBarMousePressedY);
                }
            }
        }
    }

    // BorderPane鼠标双击事件
    @FXML
    public void onTitleBarDoubleClicked(MouseEvent mouseEvent) {
        if (titleBar.getCursor() == Cursor.DEFAULT) {
            // 如果鼠标的位置不是最小化、最大化、关闭/退出按钮的范围
            if (mouseEvent.getSceneX() < titleBar.getWidth() - (ivMinimize.getFitWidth() + ivMaximize.getFitWidth() + ivExit.getFitWidth())) {
                if (mouseEvent.getClickCount() == 2) {
                    this.onClickedMaximize(mouseEvent);
                }
            }
        }
    }

}
