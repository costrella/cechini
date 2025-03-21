package com.costrella.cechini.aop;

import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.StoreRepository;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.StoreDTO;
import com.costrella.cechini.service.mapper.StoreMapper;
import com.costrella.cechini.service.mapper.StoreMapperSimple;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Component
public class StoreAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreMapperSimple storeMapperSimple;

    @Before(value = "execution(* com.costrella.cechini.service.StoreService.save(..)) && args(storeDTO, ..)")
    public void onSave(JoinPoint joinPoint, StoreDTO storeDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                storeDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Before(value = "execution(* com.costrella.cechini.service.StoreService.update(..)) && args(storeDTO, ..)")
    public void onUpdate(JoinPoint joinPoint, StoreDTO storeDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                storeDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.service.StoreService.findAll(..)) && args(pageable)")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                Store example = new Store();
                example.setTenant(loggedInUser.getTenant());
                return storeRepository.findAll(Example.of(example), pageable).map(storeMapper::toDto);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.StoreService.findAll())")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                Store example = new Store();
                example.setTenant(loggedInUser.getTenant());
                return storeRepository.findAll(Example.of(example)).stream()
                    .map(storeMapperSimple::toDto).collect(Collectors.toList());
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.StoreService.findOne(..)) && args(id, ..)")
    public Object onFindOne(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<StoreDTO> optional = (Optional<StoreDTO>) pjp.proceed();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                if (optional.isPresent() && !optional.get().getTenant().equals(loggedInUser.getTenant())) {
                    return Optional.empty();
                }
            }
        }
        return optional;
    }

    @Around("execution(* com.costrella.cechini.repository.StoreRepository.findAllByWorkerId(..)) && args(id, pageable))")
    public Object onFindAllByWorkerId(ProceedingJoinPoint pjp, Long id, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return storeRepository.findAllByWorkerIdAndTenantId(id, loggedInUser.getTenant().getId(), pageable);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.StoreService.findAllByWorkerId(..)) && args(id))")
    public Object onFindAllByWorkerId(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                return storeRepository.findAllByWorkerIdAndTenantId(id, loggedInUser.getTenant().getId())
                    .stream()
                    .map(storeMapper::toDto).collect(Collectors.toList());
            }
        }
        return pjp.proceed();
    }

    @Before(value = "execution(* com.costrella.cechini.service.StoreService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Store store = storeRepository.findById(id).get();
                if(store.getTenant() != loggedInUser.getTenant()){
                    throw new NoSuchElementException();
                }
            }
        }
    }

}
