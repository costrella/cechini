package com.costrella.cechini.aop;

import com.costrella.cechini.domain.TenantParameter;
import com.costrella.cechini.domain.User;
import com.costrella.cechini.repository.UserRepository;
import com.costrella.cechini.security.SecurityUtils;
import com.costrella.cechini.service.dto.UserDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Aspect
@Component
public class UserAspect {

@Autowired
private UserRepository userRepository;

@Autowired
private TenantParameter tenantParameter;

private final String fieldName =  "companyId";

private final Logger log = LoggerFactory.getLogger(UserAspect.class);

    /**
     * Run method if User service createUser is hit.
     * Stores tenant information from DTO.
     */
    @Before(value = "execution(* com.costrella.cechini.service.UserService.createUser(..)) && args(userDTO, ..)")
    public void onCreateUser(JoinPoint joinPoint, UserDTO userDTO) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getTenant() != null) {
                tenantParameter.setTenant(loggedInUser.getTenant());
            }
            else{
                tenantParameter.setTenant(userDTO.getTenant());
            }
        }
    }

    /**
     * Run method if User service updateUser is hit.
     * Adds tenant information to DTO.
     */
    @Before(value = "execution(*  com.costrella.cechini.service.UserService.updateUser(..)) && args(userDTO, ..)")
    public void onUpdateUser(JoinPoint joinPoint, UserDTO userDTO)
    {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent())
        {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            User user = userRepository.findById(userDTO.getId()).get();

            if (loggedInUser.getTenant() != null)
            {
                user.setTenant(loggedInUser.getTenant());
            }
            else
            {
                user.setTenant(userDTO.getTenant());
            }

            log.debug("Changed Company for User: {}", user);
        }
    }

    /**
     * Run method if User repository save is hit.
     * Adds tenant information to DTO.
     */
    @Before(value = "execution(* com.costrella.cechini.repository.UserRepository.save(..)) && args(user, ..)")
    public void onSave(JoinPoint joinPoint, User user) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(tenantParameter.getTenant() != null) {
            user.setTenant(tenantParameter.getTenant());
        }
    }

    /**
     * Run method if User service getUserWithAuthoritiesByLogin is hit.
     * Adds filtering to prevent display of information from another tenant
     */
    @Before(value = "execution(* com.costrella.cechini.service.UserService.getUserWithAuthoritiesByLogin(..)) && args(login, ..)")
    public void onGetUserWithAuthoritiesByLogin(JoinPoint joinPoint, String login) throws Exception {
        Optional<String> currentLogin = SecurityUtils.getCurrentUserLogin();

        if(currentLogin.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(currentLogin.get()).get();

            if (loggedInUser.getTenant() != null) {
                User user = userRepository.findOneWithAuthoritiesByLogin(login).get();

                if(!user.getTenant().equals(loggedInUser.getTenant())){
                    throw new NoSuchElementException();
                }
            }
        }
    }

    /**
     * Run method if User service getUserWithAuthorities is hit.
     * Adds filtering to prevent display of information from another tenant
     */
    @Before(value = "execution(* com.costrella.cechini.service.UserService.getUserWithAuthorities(..)) && args(id, ..)")
    public void onGetUserWithAuthorities(JoinPoint joinPoint, Long id) throws Exception {
        Optional<String> currentLogin = SecurityUtils.getCurrentUserLogin();

        if(currentLogin.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(currentLogin.get()).get();

            if (loggedInUser.getTenant() != null) {
                User user = userRepository.findOneWithAuthoritiesById(id).get();

                if(!user.getTenant().equals(loggedInUser.getTenant())){
                    throw new NoSuchElementException();
                }
            }
        }
    }


}
