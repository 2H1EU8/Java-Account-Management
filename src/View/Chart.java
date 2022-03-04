package View;

import Common.DAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;

public class Chart extends JFrame {
    public Chart(){
        JFreeChart pieChart = createChart(createDataset());
        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Security Password");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Security Passwords Check", dataset, true, true, true);
        return chart;
    }

    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int week = DAO.weekPass().size();
        int total = DAO.Total().size();
        dataset.setValue("Week Password",week);
        dataset.setValue("Strong Password",total-week);
        return dataset;
    }
}
