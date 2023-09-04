package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Entity.UserRole;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Repository.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @GetMapping(path = "/user")
    private List<User> userList() {
        try{
            return userRepository.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @GetMapping(path = "/userRole")
    private List<UserRole> userRoleList() {
        try{
            return userRoleRepository.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
