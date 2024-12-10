package com.PBW.RanTreker.Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {

    @Autowired
    private JDBCChartRepository chartRepository;

    public JFreeChart createWeeklyChart(int userId) {
        return createBarChart("Weekly Distance", "Week", "Distance (KM)", chartRepository.fetchWeeklyData(userId));
    }

    public JFreeChart createMonthlyChart(int userId) {
        return createBarChart("Monthly Distance", "Month", "Distance (KM)", chartRepository.fetchMonthlyData(userId));
    }

    public JFreeChart createYearlyChart(int userId) {
        return createBarChart("Yearly Distance", "Year", "Distance (KM)", chartRepository.fetchYearlyData(userId));
    }

    private JFreeChart createBarChart(String title, String categoryAxisLabel, String valueAxisLabel, List<ChartData> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ChartData item : data) {
            dataset.addValue(item.getValue(), "Distance", item.getLabel());
        }
        return ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset);
    }
}
