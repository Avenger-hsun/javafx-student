package org.kuro.student.controller.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kuro.student.application.SpringFXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class LeftController {

    // 左侧 [驾驶舱] 标签Tab
    @FXML
    private HBox hDashboardTab;

    // 左侧 [学生管理] 标签Tab
    @FXML
    private HBox hBoxStudentManageTab;

    // 左侧 [本地成绩] 标签Tab
    @FXML
    private HBox hBoxLocalGradeTab;

    // 左侧 [成绩管理] 标签Tab
    @FXML
    private HBox hBoxGradeManageTab;

    // 装标签的集合tabList
    private List<HBox> tabList;

    // 存储标签的VBox容器
    @FXML
    private VBox vBoxTabContainer;

    // 注入窗体根容器（BorderPane）的控制类
    @Resource
    private MainController mainController;

    // 注入窗体根容器（BorderPane）的中间容器的控制器
    @Resource
    private CenterController centerController;

    // 注入Spring上下文工具类
    @Resource
    private ApplicationContext applicationContext;

    // 记录选择的标签索引
    private int selectedTabIndex = -1;

    public VBox getVBoxTabContainer() {
        return vBoxTabContainer;
    }

    public List<HBox> getTabList() {
        return tabList;
    }

    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    // 设置选择的标签背景颜色的函数
    public void setSelectedTab(HBox selectedTab) {
        this.resetSelectedTab();
        // 然后给当前选中的标签的parent容器添加css类名
        selectedTab.getStyleClass().add("selectedHBox");
        selectedTabIndex = tabList.indexOf(selectedTab);
    }

    public HBox getSelectedTab() {
        return tabList.get(selectedTabIndex);
    }

    public void resetSelectedTab() {
        // 重置所有的标签的背景颜色，我这里的HBox标签背景颜色是由另外一个HBox包裹做背景颜色显示的，所以需要getParent，设置parent的样式
        for (HBox hBoxTab : tabList) {
            // 移除parent的css类名
            hBoxTab.getStyleClass().remove("selectedHBox");
        }
    }

    public void initialize() throws IOException {
        tabList = new LinkedList<>();
        tabList.add(hDashboardTab);
        tabList.add(hBoxLocalGradeTab);
        tabList.add(hBoxGradeManageTab);
        tabList.add(hBoxStudentManageTab);

        // 初始化 [驾驶舱] 为选择的标签
        this.setSelectedTab(hDashboardTab);
    }


    // 单击[驾驶舱]标签事件处理
    @FXML
    public void onClickedDashboardTab(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // 普通单击时,不是当前选中的tab
            if (getSelectedTab() != hDashboardTab) {
                this.setSelectedTab(hDashboardTab);
                setCenterPage("/layout/dashboard/dashboard.fxml");
            } else if (mouseEvent.getClickCount() == 5) {
                this.setSelectedTab(hDashboardTab);
                setCenterPage("/layout/dashboard/dashboard.fxml");
            }
        }
    }


    // 单击[学生管理]标签事件处理
    @FXML
    public void onClickedStudentManageTab(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (getSelectedTab() != hBoxStudentManageTab) {
                this.setSelectedTab(hBoxStudentManageTab);
                setCenterPage("/layout/student/student-manage.fxml");
            } else if (mouseEvent.getClickCount() == 5) {
                this.setSelectedTab(hBoxStudentManageTab);
                setCenterPage("/layout/student/student-manage.fxml");
            }
        }
    }


    // 单击[本地成绩]标签事件处理
    @FXML
    public void onClickedLocalGradeTab(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {  //鼠标左击
            if (getSelectedTab() != hBoxLocalGradeTab) {
                // 设置当前选择的为[本地成绩]标签
                this.setSelectedTab(hBoxLocalGradeTab);
                setCenterPage("/layout/student/local-grade.fxml");
            } else if (mouseEvent.getClickCount() == 5) {
                this.setSelectedTab(hBoxLocalGradeTab);
                setCenterPage("/layout/student/local-grade.fxml");
            }
        }
    }


    // 单击[成绩管理]标签事件处理
    @FXML
    public void onClickedGradeManageTab(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // 普通单击时,不是当前选中的tab
            if (getSelectedTab() != hBoxGradeManageTab) {
                this.setSelectedTab(hBoxGradeManageTab);
                setCenterPage("/layout/student/grade-manage.fxml");
            } else if (mouseEvent.getClickCount() == 5) {
                this.setSelectedTab(hBoxGradeManageTab);
                setCenterPage("/layout/student/grade-manage.fxml");
            }
        }
    }


    /**
     * 设置页面
     *
     * @param resource fxml文件路径
     */
    private void setCenterPage(String resource) throws IOException {
        FXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class).getLoader(resource);
        centerController.getBorderPane().setCenter(loader.load());
    }
}
