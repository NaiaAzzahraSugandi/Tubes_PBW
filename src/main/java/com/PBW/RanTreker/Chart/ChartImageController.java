package com.PBW.RanTreker.Chart;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Controller
public class ChartImageController {

    @Autowired
    private ChartImageRepository chartImageRepository;

    @GetMapping("/chart-image")
    public ResponseEntity<byte[]> getChartImage() {
        Optional<byte[]> imageData = chartImageRepository.getLatestChartImage();

        if (imageData.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
            return new ResponseEntity<>(imageData.get(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}