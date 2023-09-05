package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Company;
import com.umg.charly.nomina.Entity.Option;
import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Entity.UserRole;
import com.umg.charly.nomina.Repository.CompanyRepository;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Repository.UserRoleRepository;
import com.umg.charly.nomina.Tools.Encoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
                System.out.println(validateRules(user.getPassword()));
                if (validateRules(user.getPassword())) {
                    String cript = new Encoding().crypt(user.getPassword());
                    dataUser.setPassword(cript);
                    userRepository.save(dataUser);
                    return "Exitoso";
                }
                return "No cumple las condiciones";
            }
            return "Cliente no existe";
        }
        return "Parametros incorrectos";
    }


    private Boolean validateRules(String dataPass) {
        if (dataPass != null) {
            Optional<Company> dataCompany = companyRepository.findById(1L);
            int mayusculasRequeridas = dataCompany.get().getPasswordAmountUppercase();
            int minusculasRequeridas = dataCompany.get().getPasswordAmountLowercase();
            int numerosRequeridos = dataCompany.get().getPasswordAmountNumber();
            int simbolosRequeridos = dataCompany.get().getPasswordAmountSpecialCharacters();
            int longitudMinima = dataCompany.get().getPasswordlength();
            int longitudMaxima = 25;

            if (dataPass.length() < longitudMinima || dataPass.length() > longitudMaxima) {
                return false;
            }
            int mayusculasEncontradas = 0;
            int minusculasEncontradas = 0;
            int numerosEncontrados = 0;
            int simbolosEncontrados = 0;

            for (int i = 0; i < dataPass.length(); i++) {
                char caracter = dataPass.charAt(i);
                if (Character.isUpperCase(caracter)) {
                    mayusculasEncontradas++;
                } else if (Character.isLowerCase(caracter)) {
                    minusculasEncontradas++;
                } else if (Character.isDigit(caracter)) {
                    numerosEncontrados++;
                } else {
                    simbolosEncontrados++;
                }
            }

            if (mayusculasEncontradas >= mayusculasRequeridas) {
                System.out.println("mayusculas -> OK " + mayusculasEncontradas);
                if (minusculasEncontradas >= minusculasRequeridas) {
                    System.out.println("minusculas -> OK "+minusculasEncontradas);
                    if (numerosEncontrados >= numerosRequeridos) {
                        System.out.println("numeros -> OK "+numerosEncontrados);
                        if (simbolosEncontrados >= simbolosRequeridos) {
                            System.out.println("simbolos -> OK "+simbolosEncontrados);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}
