package com.costrella.cechini.aop;


import com.costrella.cechini.domain.Photo;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.PhotoRepository;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.PhotoDTO;
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
public class PhotoAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Before(value = "execution(* com.costrella.cechini.service.PhotoService.save(..)) && args(photoDTO, ..)")
    public void onSave(JoinPoint joinPoint, PhotoDTO photoDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                photoDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.repository.PhotoRepository.findAll(..)) && args(pageable))")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return photoRepository.findAllByTenantId(loggedInUser.getTenant().getId(), pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.PhotoService.findAll()))")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return photoRepository.findAllByTenantId(loggedInUser.getTenant().getId());
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.repository.PhotoRepository.findOne(..)) && args(id))")
    public Object onFindOne(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return photoRepository.findByTenantIdAndId(loggedInUser.getTenant().getId(), id);
            }
        }
        return pjp.proceed();
    }

    @Before(value = "execution(* com.costrella.cechini.service.PhotoService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Photo photo = photoRepository.findById(id).get();
                if (photo.getTenant() != loggedInUser.getTenant()) {
                    throw new NoSuchElementException();
                }
            }
        }
    }
}
