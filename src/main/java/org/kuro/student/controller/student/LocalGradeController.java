package org.kuro.student.controller.student;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.kuro.student.application.SpringFXMLLoader;
import org.kuro.student.entity.LocalStudent;
import org.kuro.student.mapper.LocalStudentMapper;
import org.kuro.student.utils.StageUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Controller
public class LocalGradeController {

    // 表格和列
    @FXML
    public TableView<LocalStudent> tableStudentLocal;
    @FXML
    public TableColumn<LocalStudent, String> noColumn;
    @FXML
    public TableColumn<LocalStudent, String> nameColumn;
    @FXML
    public TableColumn<LocalStudent, String> courseColumn;
    @FXML
    public TableColumn<LocalStudent, String> gradeColumn;
    @FXML
    public TableColumn<LocalStudent, String> actionColumn;
    // 本地导入[成功]条数显示label
    @FXML
    public Label importSuccessLabel;
    // 本地导入[失败]条数显示label
    @FXML
    public Label importFailedLabel;
    // 根据姓名筛选的文本框
    @FXML
    public TextField nameSearch;
    // 根据课程筛选的文本框
    @FXML
    public TextField courseSearch;

    // 注入Spring上下文类
    @Resource
    private ApplicationContext applicationContext;

    // 文件选择器
    private FileChooser fileChooser;
    // 本地导入的学生列表
    public final List<LocalStudent> students = new ArrayList<>();
    // 存放每次读取txt文本得到了学生数据
    private final List<LocalStudent> readList = new ArrayList<>();
    // 导入成绩错误的信息列表
    public final List<String> errList = new ArrayList<>();
    // 导入成功/失败的数据条数
    int importSuccessNum = 0, importFailedNum = 0;


    public void initialize() {
        // 添加css名称.在CSS文件定制样式
        noColumn.getStyleClass().add("noColumn");
        nameColumn.getStyleClass().add("nameColumn");
        courseColumn.getStyleClass().add("courseColumn");
        gradeColumn.getStyleClass().add("gradeColumn");
        actionColumn.getStyleClass().add("actionColumn");

        noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        // 自定义操作单元格的内容
        actionColumn.setCellFactory(param -> new TableCell<LocalStudent, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    try {
                        FXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class)
                                .getLoader("/layout/student/table-action.fxml");
                        setGraphic(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 切换Tab的时候表格的数据会清空，但是students的数据还在，这里判断students的大小重新设置表格数据
        setTableData();

        // 关闭表格"头"列的左右拖拽移动重新排列行为
        tableStudentLocal.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tableStudentLocal.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });

        // 设置表格列的宽度随这个borderPane的宽度而动态改变
        tableStudentLocal.widthProperty().addListener((observable, oldValue, newValue) -> {
            noColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.2);
            nameColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.1);
            courseColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.6);
            gradeColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.03);
            actionColumn.setPrefWidth(observable.getValue().doubleValue() / 6.5 * 1.6);
        });

        // 初始化文件选择器
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt文件", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
    }


    // [本地导入]按钮点击事件
    @FXML
    public void onClickedImport(ActionEvent actionEvent) throws IOException {
        File file = fileChooser.showOpenDialog(null);
        if (file != null && file.isFile() && file.exists()) {
            // 成功或失败数的统计数置0
            importSuccessNum = 0;
            importFailedNum = 0;
            // 上一次读取到的学生信息置空
            readList.clear();
            // 导入错误成绩信息置空
            errList.clear();

            // 读取文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                // 如果读取的行数不包含【学号】，则表示不是表头行
                if (!line.contains("学号")) {
                    String[] split = line.split("，");
                    if (split.length == 4 && Double.parseDouble(split[3]) <= 100 && Double.parseDouble(split[3]) >= 0) {
                        LocalStudent student = new LocalStudent(
                                IdUtil.getSnowflakeNextIdStr(),
                                split[0], split[1], split[2], split[3]);
                        readList.add(student);
                        importSuccessNum++;
                    } else {
                        importFailedNum++;
                        // 记录导入错误的信息
                        errList.add(line);
                    }
                }
            }

            // 将读取的学生信息放入students集合中，并刷新表格数据
            students.addAll(readList);
            setTableData();

            // 设置本次导入成功或失败的数量
            importSuccessLabel.setText("成功: " + importSuccessNum);
            importFailedLabel.setText("失败: " + importFailedNum);

            // 如果存在导出错误的信息，弹框显示
            if (errList.size() > 0) {
                Stage stage = ((Stage) tableStudentLocal.getScene().getWindow());
                showDialog("/layout/dialog/import-error-dialog.fxml", stage);
            }
        }
    }


    // [查询]按钮点击事件
    @FXML
    public void onClickedSearch(ActionEvent actionEvent) {
        // 如果搜索框为空阻止代码向下运行
        String nameText = nameSearch.getText().trim();
        String courseText = courseSearch.getText().trim();
        if (nameText.isEmpty() && courseText.isEmpty()) return;

        ObservableList<LocalStudent> items = FXCollections.observableArrayList(students);
        if (nameText.isEmpty()) { // 姓名为空，课程不为空
            tableStudentLocal.setItems(items.filtered(stu -> stu.getCourse().equals(courseText)));
        } else if (courseText.isEmpty()) { // 课程为空，姓名不为空
            tableStudentLocal.setItems(items.filtered(stu -> stu.getName().equals(nameText)));
        } else { // 姓名、课程都不为空
            tableStudentLocal.setItems(items.filtered(
                    stu -> stu.getName().equals(nameText) && stu.getCourse().equals(courseText)));
        }
        tableStudentLocal.refresh();
    }


    // [清除]按钮点击事件
    @FXML
    public void onClickedClear(ActionEvent actionEvent) {
        // 文本框置空
        nameSearch.setText("");
        courseSearch.setText("");

        // 重新设置表格数据
        ObservableList<LocalStudent> items = FXCollections.observableArrayList(students);
        tableStudentLocal.setItems(items);
    }


    // [添加学生]按钮点击事件
    @FXML
    public void onClickedAddStudent(ActionEvent actionEvent) throws Exception {
        Stage stage = ((Stage) tableStudentLocal.getScene().getWindow());
        showDialog("/layout/dialog/add-student-local-dialog.fxml", stage);
    }


    // [保存到数据库]按钮点击事件
    public void onClickedSaveDB(ActionEvent actionEvent) {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = factory.openSession();

            LocalStudentMapper studentMapper = session.getMapper(LocalStudentMapper.class);
            studentMapper.saveList(students);
            session.commit();
            session.close();
            reader.close();

            System.out.println("数据已保存到数据库！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // [启动服务]按钮点击事件
    boolean serverClicked = false;
    public void onClickedServer(ActionEvent actionEvent) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "请求成功！");
        map.put("success", true);

        // 开启一个新的线程，并开启一个http服务返回学生成绩列表
        if (!serverClicked) {
            ExecutorService service = ThreadUtil.newExecutor();
            service.execute(() -> HttpUtil.createServer(8360)
                    .addAction("/", (req, res) -> {
                        map.put("data", students);
                        res.write(JSONUtil.toJsonStr(map), ContentType.JSON.toString());
                    }).start());
            serverClicked = true;
            service.shutdown();
        }
    }


    // 设置表格的数据
    public void setTableData() {
        tableStudentLocal.setItems(FXCollections.observableArrayList(students));
        tableStudentLocal.refresh();
    }


    // 显示弹框
    private void showDialog(String url, Stage stage) throws IOException {
        FXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class).getLoader(url);
        Stage dialogStage = StageUtils.getStage(stage, loader.load());
        StageUtils.synchronizeCenter(stage, dialogStage);
        // WindowUtils.blockBorderPane(mainController.getBorderPane());
        dialogStage.showAndWait();
    }


}
