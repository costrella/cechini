package com.costrella.cechini.aop;


import com.costrella.cechini.domain.Warehouse;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.WarehouseRepository;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.WarehouseDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Aspect
@Component
public class WarehouseAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Before(value = "execution(* com.costrella.cechini.service.WarehouseService.save(..)) && args(warehouseDTO, ..)")
    public void onSave(JoinPoint joinPoint, WarehouseDTO warehouseDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                warehouseDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.repository.WarehouseRepository.findAll(..)) && args(pageable))")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return warehouseRepository.findAllByTenantId(loggedInUser.getTenant().getId(), pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.WarehouseService.findAll()))")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return warehouseRepository.findAllByTenantId(loggedInUser.getTenant().getId());
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.WarehouseRepository.findOne(..)) && args(id))")
    public Object onFindOne(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return warehouseRepository.findByTenantIdAndId(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Before(value = "execution(* com.costrella.cechini.service.WarehouseService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Warehouse warehouse = warehouseRepository.findById(id).get();
                if (warehouse.getTenant() != loggedInUser.getTenant()) {
                    throw new NoSuchElementException();
                }
            }
        }
    }
}
