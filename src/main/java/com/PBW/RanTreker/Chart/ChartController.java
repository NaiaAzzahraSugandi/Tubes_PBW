package com.PBW.RanTreker.Chart;

import org.jfree.chart.ChartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

public class ChartController {

    @Autowired
    private ChartService chartService;

    // @GetMapping("/chart/weekly")
    // @ResponseBody
    // public void getWeeklyChart(@RequestParam int userId, HttpServletResponse response) throws IOException {
    //     var chart = chartService.createWeeklyChart(userId);
    //     response.setContentType("image/png");
    //     ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 600);
    // }

    // @GetMapping("/chart/monthly")
    // @ResponseBody
    // public void getMonthlyChart(@RequestParam int userId, HttpServletResponse response) throws IOException {
    //     var chart = chartService.createMonthlyChart(userId);
    //     response.setContentType("image/png");
    //     ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 600);
    // }

    // @GetMapping("/chart/yearly")
    // @ResponseBody
    // public void getYearlyChart(@RequestParam int userId, HttpServletResponse response) throws IOException {
    //     var chart = chartService.createYearlyChart(userId);
    //     response.setContentType("image/png");
    //     ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 600);
    // }
}
