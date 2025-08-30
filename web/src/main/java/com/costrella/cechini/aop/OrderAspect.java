package com.costrella.cechini.aop;


import com.costrella.cechini.domain.Order;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.OrderRepository;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.OrderDTO;
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
public class OrderAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Before(value = "execution(* com.costrella.cechini.service.OrderService.save(..)) && args(orderDTO, ..)")
    public void onSave(JoinPoint joinPoint, OrderDTO orderDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                orderDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Before(value = "execution(* com.costrella.cechini.repository.OrderRepository.save(..)) && args(order, ..)")
    public void onSave(JoinPoint joinPoint, Order order) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                order.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.repository.OrderRepository.findAll(..)) && args(pageable))")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return orderRepository.findAllByTenantId(loggedInUser.getTenant().getId(), pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.OrderService.findAll()))")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return orderRepository.findAllByTenantId(loggedInUser.getTenant().getId());
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.OrderRepository.findOne(..)) && args(id))")
    public Object onFindOne(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return orderRepository.findByTenantIdAndId(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Before(value = "execution(* com.costrella.cechini.service.OrderService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Order order = orderRepository.findById(id).get();
                if (order.getTenant() != loggedInUser.getTenant()) {
                    throw new NoSuchElementException();
                }
            }
        }
    }
}
