package org.kuro.student.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {

    @FXML
    public PieChart pieChart;

    public void initialize() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("不及格", 3),
                new PieChart.Data("及格", 32),
                new PieChart.Data("中等", 35),
                new PieChart.Data("良好", 20),
                new PieChart.Data("优秀", 10));

        pieChart.setData(data);
    }
}
