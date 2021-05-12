package com.costrella.cechini.web.rest;

import com.costrella.cechini.service.StatsService;
import com.costrella.cechini.service.dto.Chart01DTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StatsResource {

    private final StatsService statsService;

    public StatsResource(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats/chart01")
    public ResponseEntity<Chart01DTO> getChart01() {
        return ResponseUtil.wrapOrNotFound(Optional.of(statsService.getReportsChart(7)));
    }

    @GetMapping("/stats/chart02")
    public ResponseEntity<Chart01DTO> getOrdersChart() {
        return ResponseUtil.wrapOrNotFound(Optional.of(statsService.getOrdersChart(7)));
    }

    @GetMapping("/stats/chart03")
    public ResponseEntity<Chart01DTO> getSumOfPackCountOfProducts() {
        return ResponseUtil.wrapOrNotFound(Optional.of(statsService.getSumOfPackCountOfProducts(7)));
    }

}
