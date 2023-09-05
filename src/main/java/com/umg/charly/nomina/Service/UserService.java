package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Company;
import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Entity.UserRole;
import com.umg.charly.nomina.Repository.CompanyRepository;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Repository.UserRoleRepository;
import com.umg.charly.nomina.Tools.Encoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(path = "/user")
    private List<User> userList() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @GetMapping(path = "/userRole")
    private List<UserRole> userRoleList() {
        try {
            return userRoleRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping(path = "/resetPassword")
    private String reset(@RequestBody User user) {
        if (user.getIdUser() != null) {
            User dataUser = userRepository.findByIdUser(user.getIdUser());
            if (dataUser != null) {
                List<Company> dataCompany = companyRepository.findAll();
               int plength = dataCompany.get(0).getPasswordlength();
                if(user.getPassword().length() <= plength ){
                    String cript = new Encoding().crypt(user.getPassword());
                    dataUser.setPassword(cript);
                    userRepository.save(dataUser);
                    return "Exitoso";
                }
                return "Contraseña demasiado larga";
            }
            return "Cliente no existe";
        }
        return "Parametros incorrectos";
    }


}
