package com.costrella.cechini.aop;


import com.costrella.cechini.domain.User;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.repository.WorkerRepository;
import com.costrella.cechini.security.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class WorkerAspect {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Around("execution(* com.costrella.cechini.service.WorkerService.findAll(..)) && args(pageable)")
    public Object onFindAll(ProceedingJoinPoint pjp, Pageable pageable) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent())
        {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                Worker example = new Worker();
                example.setTenant(loggedInUser.getTenant());
                return workerRepository.findAll(Example.of(example), pageable);
            }
        }
        return pjp.proceed();
    }

}
