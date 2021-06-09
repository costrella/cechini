package com.costrella.cechini.service;

import com.costrella.cechini.domain.Product;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.ProductRepository;
import com.costrella.cechini.repository.StatsRepository;
import com.costrella.cechini.service.dto.Chart01DTO;
import com.costrella.cechini.service.dto.ChartDetail01DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
@Transactional
public class StatsService {

    private final Logger log = LoggerFactory.getLogger(StatsService.class);

    private final StatsRepository statsRepository;

    private final ProductRepository productRepository;

    public StatsService(StatsRepository statsRepository, ProductRepository productRepository) {
        this.statsRepository = statsRepository;
        this.productRepository = productRepository;
    }

    public Chart01DTO getReportsChart(int monthsAgo) {
        Chart01DTO chart = new Chart01DTO();
        chart.details = new ArrayList<>();
        chart.monthsName = new ArrayList<>();
        LocalDate now = LocalDate.now();
        Random obj = new Random();
        int rand_num;
        List<Worker> workers = statsRepository.findAll();
        boolean fillMonths = true;
        for (Worker worker : workers) {
            ChartDetail01DTO chartDetail = new ChartDetail01DTO();
            rand_num = obj.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", rand_num);
            chartDetail.borderColor = colorCode;
            chartDetail.pointBackgroundColor = colorCode;
            chartDetail.data = new ArrayList<>();
            for (int i = monthsAgo; i >= 0; i--) {
                chartDetail.data.add(statsRepository.getNumberOfReportsFromXMonthAgo(worker.getId(), "" + i));
                if (fillMonths)
                    chart.monthsName.add(now.minusMonths(i).getMonth().getDisplayName(
                        TextStyle.FULL_STANDALONE,
                        Locale.forLanguageTag("PL")
                    ));
            }

            chartDetail.label = worker.getSurname() + " " + worker.getName();
            chart.details.add(chartDetail);
            fillMonths = false;
        }

        return chart;
    }


    public Chart01DTO getOrdersChart(int monthsAgo) {
        Chart01DTO chart = new Chart01DTO();
        chart.details = new ArrayList<>();
        chart.monthsName = new ArrayList<>();
        LocalDate now = LocalDate.now();
        Random obj = new Random();
        int rand_num;
        List<Worker> workers = statsRepository.findAll();
        boolean fillMonths = true;
        for (Worker worker : workers) {
            ChartDetail01DTO chartDetail = new ChartDetail01DTO();
            rand_num = obj.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", rand_num);
            chartDetail.borderColor = colorCode;
            chartDetail.pointBackgroundColor = colorCode;
            chartDetail.data = new ArrayList<>();
            for (int i = monthsAgo; i >= 0; i--) {
                chartDetail.data.add(statsRepository.getNumberOfOrdersFromXMonthAgo(worker.getId(), "" + i));
                if (fillMonths)
                    chart.monthsName.add(now.minusMonths(i).getMonth().getDisplayName(
                        TextStyle.FULL_STANDALONE,
                        Locale.forLanguageTag("PL")
                    ));
            }

            chartDetail.label = worker.getSurname() + " " + worker.getName();
            chart.details.add(chartDetail);
            fillMonths = false;
        }

        return chart;
    }

    public Chart01DTO getSumOfPackCountOfProducts(int monthsAgo) {
        Chart01DTO chart = new Chart01DTO();
        chart.details = new ArrayList<>();
        chart.monthsName = new ArrayList<>();
        LocalDate now = LocalDate.now();
        Random obj = new Random();
        int rand_num;
        List<Product> products = productRepository.findAll();
        boolean fillMonths = true;
        for (Product product : products) {
            ChartDetail01DTO chartDetail = new ChartDetail01DTO();
            rand_num = obj.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", rand_num);
            chartDetail.borderColor = colorCode;
            chartDetail.pointBackgroundColor = colorCode;
            chartDetail.data = new ArrayList<>();
            for (int i = monthsAgo; i >= 0; i--) {
                chartDetail.data.add(statsRepository.getSumOfPackCountOfProduct(product.getId(), "" + i));
                if (fillMonths)
                    chart.monthsName.add(now.minusMonths(i).getMonth().getDisplayName(
                        TextStyle.FULL_STANDALONE,
                        Locale.forLanguageTag("PL")
                    ));
            }

            chartDetail.label = product.getName() + " (" + product.getCapacity() + "L)";
            chart.details.add(chartDetail);
            fillMonths = false;
        }

        return chart;
    }
}
