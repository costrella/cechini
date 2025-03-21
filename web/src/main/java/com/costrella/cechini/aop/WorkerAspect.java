package com.costrella.cechini.aop;


import com.costrella.cechini.domain.User;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.repository.WorkerRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.WorkerDTO;
import com.costrella.cechini.service.mapper.WorkerMapper;
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
public class WorkerAspect {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerMapper workerMapper;

    @Before(value = "execution(* com.costrella.cechini.service.WorkerService.save(..)) && args(workerDTO, ..)")
    public void onSave(JoinPoint joinPoint, WorkerDTO workerDTO) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                workerDTO.setTenant(loggedInUser.getTenant());
            }
        }
    }

    @Around("execution(* com.costrella.cechini.service.WorkerService.findAll(..)) && args(pageable)")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                Worker example = new Worker();
                example.setTenant(loggedInUser.getTenant());
                return workerRepository.findAll(Example.of(example), pageable).map(workerMapper::toDto);
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.WorkerService.findAll())")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            if (loggedInUser.getTenant() != null) {
                Worker example = new Worker();
                example.setTenant(loggedInUser.getTenant());
                return workerRepository.findAll(Example.of(example)).stream()
                    .map(workerMapper::toDto).collect(Collectors.toList());
            }
        }
        return pjp.proceed();
    }

    @Around("execution(* com.costrella.cechini.service.WorkerService.findOne(..)) && args(id, ..)")
    public Object onFindOne(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<WorkerDTO> optional = (Optional<WorkerDTO>) pjp.proceed();
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

    @Around("execution(* com.costrella.cechini.service.WorkerService.findByLogin(..)) && args(login1, ..)")
    public Object onFindByLogin(ProceedingJoinPoint pjp, String login1) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<WorkerDTO> optional = (Optional<WorkerDTO>) pjp.proceed();
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

    @Around("execution(* com.costrella.cechini.service.WorkerService.findByLoginAndPassword(..)) && args(login1, password, ..)")
    public Object onFindByLoginAndPassword(ProceedingJoinPoint pjp, String login1, String password) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<WorkerDTO> optional = (Optional<WorkerDTO>) pjp.proceed();
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

    @Before(value = "execution(* com.costrella.cechini.service.WorkerService.delete(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Worker worker = workerRepository.findById(id).get();
                if(worker.getTenant() != loggedInUser.getTenant()){
                    throw new NoSuchElementException();
                }
            }
        }
    }

}
