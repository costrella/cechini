package com.costrella.cechini.aop;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.ReportRepository;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.ReportDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Aspect
@Component
public class ReportAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Before(value = "execution(* com.costrella.cechini.service.ReportService.save(..)) && args(reportDTO, ..)")
    public void onSave(JoinPoint joinPoint, ReportDTO reportDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                reportDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Before(value = "execution(* com.costrella.cechini.service.ReportService.save(..)) && args(report, ..)")
    public void onSave(JoinPoint joinPoint, Report report) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                report.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Before(value = "execution(* com.costrella.cechini.service.ReportService.saveWithPhotos(..)) && args(reportDTO, ..)")
    public void onSaveWithPhotos(JoinPoint joinPoint, ReportDTO reportDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                reportDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository.findAll(..)) && args(pageable)")
    public Object findAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                Report example = new Report();
                example.setTenant(loggedInUser.getTenant());
                return reportRepository.findAll(Example.of(example), pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository.findAllByReportDateBetween(..)) && args(fromDate, toDate, pageable)")
    public Object findAllByReportDateBetween(ProceedingJoinPoint pjp, Instant fromDate, Instant toDate, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndReportDateBetween(loggedInUser.getTenant().getId(), fromDate, toDate, pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByStoreIdAndWorkerIdAndReportDateBetween(..)) && args(storeId, workerId, fromDate, toDate, pageable)")
    public Object findAllByStoreIdAndWorkerIdAndReportDateBetween(ProceedingJoinPoint pjp, Long storeId, Long workerId, Instant fromDate, Instant toDate, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndStoreIdAndWorkerIdAndReportDateBetween(loggedInUser.getTenant().getId(), storeId, workerId, fromDate, toDate, pageable);
            }
        }
        return pjp.proceed();
    }


    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByWorkerIdAndReportDateBetween(..)) && args(id, fromDate, toDate, pageable)")
    public Object findAllByStoreIdAndWorkerIdAndReportDateBetween(ProceedingJoinPoint pjp, Long id, Instant fromDate, Instant toDate, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndWorkerIdAndReportDateBetween(loggedInUser.getTenant().getId(), id, fromDate, toDate, pageable);
            }
        }
        return pjp.proceed();
    }


    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByStoreIdAndReportDateBetween(..)) && args(id, fromDate, toDate, pageable)")
    public Object findAllByStoreIdAndReportDateBetween(ProceedingJoinPoint pjp, Long id, Instant fromDate, Instant toDate, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndStoreIdAndReportDateBetween(loggedInUser.getTenant().getId(), id, fromDate, toDate, pageable);
            }
        }
        return pjp.proceed();
    }


    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByWorkerIdOrderByReportDateDesc(..)) && args(id, pageable)")
    public Object findAllByWorkerIdOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndWorkerIdOrderByReportDateDesc(loggedInUser.getTenant().getId(), id, pageable);
            }
        }
        return pjp.proceed();
    }


    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".customByWorkerIdAndReportDateBetweenOrderByReportDateDesc(..)) && args(id, from, to)")
    public Object customByWorkerIdAndReportDateBetweenOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id, Instant from, Instant to) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .customByTenantIdAndWorkerIdAndReportDateBetweenOrderByReportDateDesc(loggedInUser.getTenant().getId(), id, from, to);
            }
        }
        return pjp.proceed();
    }


    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".customFindAllByWorkerIdAndReadByWorkerIsFalseOrderByReportDateDesc(..)) && args(id)")
    public Object customFindAllByWorkerIdAndReadByWorkerIsFalseOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .customFindAllByWorkerIdAndReadByWorkerIsFalseOrderByReportDateDesc(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".customFindAllByWorkerIdAndStoreIdOrderByReportDateDesc(..)) && args(id, storeId)")
    public Object customFindAllByWorkerIdAndStoreIdOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id, Long storeId) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .customFindAllByTenantIdAndWorkerIdAndStoreIdOrderByReportDateDesc(loggedInUser.getTenant().getId(), id, storeId);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByWorkerIdAndStoreIdOrderByReportDateDesc(..)) && args(id, storeId, pageable)")
    public Object findAllByWorkerIdAndStoreIdOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id, Long storeId, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndWorkerIdAndStoreIdOrderByReportDateDesc(loggedInUser.getTenant().getId(), id, storeId, pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByStoreIdOrderByReportDateDesc(..)) && args(id, pageable)")
    public Object findAllByStoreIdOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndStoreIdOrderByReportDateDesc(loggedInUser.getTenant().getId(), id, pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findById(..)) && args(id)")
    public Object findById(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findByTenantIdAndId(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".customFindById(..)) && args(id)")
    public Object customFindById(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .customFindByTenantIdAndId(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ReportRepository" +
        ".findAllByStoreIdAndReportDateBetweenOrderByReportDateDesc(..)) && args(id, fromDate, toDate)")
    public Object findAllByStoreIdAndReportDateBetweenOrderByReportDateDesc(ProceedingJoinPoint pjp, Long id, Instant fromDate, Instant toDate) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return reportRepository
                    .findAllByTenantIdAndStoreIdAndReportDateBetweenOrderByReportDateDesc(loggedInUser.getTenant().getId(), id, fromDate, toDate);
            }
        }
        return pjp.proceed();
    }

    @Before(value = "execution(* com.costrella.cechini.service.ReportService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Report report = reportRepository.findById(id).get();
                if(report.getTenant() != loggedInUser.getTenant()){
                    throw new NoSuchElementException();
                }
            }
        }
    }

}
