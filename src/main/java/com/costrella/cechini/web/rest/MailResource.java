package com.costrella.cechini.web.rest;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.enumeration.OrderFileType;
import com.costrella.cechini.service.MailService;
import com.costrella.cechini.service.ReportService;
import com.costrella.cechini.service.dto.ReportDTO;
import com.costrella.cechini.service.dto.ReportDTOWithPhotos;
import com.costrella.cechini.service.dto.ReportsDTO;
import com.costrella.cechini.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Report}.
 */
@RestController
@RequestMapping("/api")
public class MailResource {

    private final Logger log = LoggerFactory.getLogger(MailResource.class);

    private static final String ENTITY_NAME = "mail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MailService mailService;

    public MailResource(MailService mailService) {
        this.mailService = mailService;
    }


    @GetMapping("/mail/test01")
    public ResponseEntity testMail01() {
        log.debug("Test mail 01");
        return ResponseEntity.ok().build();

    }

    @GetMapping("/mail/test02")
    public ResponseEntity testMail02() {
        log.debug("Test mail 02");
        mailService.sendEmailWithOrder("01", "misiek.mk@gmail.com", OrderFileType.CSV,null);
        return ResponseEntity.ok().build();

    }

}
