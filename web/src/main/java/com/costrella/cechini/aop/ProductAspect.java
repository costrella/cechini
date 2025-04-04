package com.costrella.cechini.aop;


import com.costrella.cechini.domain.Product;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.ProductRepository;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.ProductDTO;
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
public class ProductAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Before(value = "execution(* com.costrella.cechini.service.ProductService.save(..)) && args(productDTO, ..)")
    public void onSave(JoinPoint joinPoint, ProductDTO productDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                productDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.repository.ProductRepository.findAll(..)) && args(pageable))")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return productRepository.findAllByTenantId(loggedInUser.getTenant().getId(), pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ProductRepository.findAll()))")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return productRepository.findAllByTenantId(loggedInUser.getTenant().getId());
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.ProductRepository.findOne(..)) && args(id))")
    public Object onFindAll(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return productRepository.findByTenantIdAndId(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Before(value = "execution(* com.costrella.cechini.service.ProductService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Product product = productRepository.findById(id).get();
                if (product.getTenant() != loggedInUser.getTenant()) {
                    throw new NoSuchElementException();
                }
            }
        }
    }
}
