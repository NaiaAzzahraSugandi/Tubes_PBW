package com.PBW.RanTreker.Chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class ChartImageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveChartImage(byte[] imageData) {
        String sql = "INSERT INTO chart_images (image_data) VALUES (?)";
        jdbcTemplate.update(sql, imageData);
    }

    public Optional<byte[]> getLatestChartImage() {
        String sql = "SELECT image_data FROM chart_images ORDER BY created_at DESC LIMIT 1";
        return jdbcTemplate.query(sql, rs -> rs.next() ? Optional.of(rs.getBytes("image_data")) : Optional.empty());
    }
}