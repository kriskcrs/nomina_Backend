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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    //Message
    String fails = "Su contraseña no cumple con las condiciones ";
    String errorClient = "Cliente no existe";
    String errorParameters = "Error en parametros";
    String OK ="Exitoso";
    int MaxCharterPassword = 25;
    HashMap<String, String>  response = new HashMap<>();

    @GetMapping(path = "/user")
    private List<User> userList() {
            return userRepository.findAll();
    }

    @GetMapping(path = "/user/{id}")
    private User userFind(@PathVariable String id){
        return userRepository.findByIdUser(id);
    }

    @GetMapping(path = "/userRole")
    private List<UserRole> userRoleList() {
            return userRoleRepository.findAll();
    }

    @PostMapping(path = "/resetPassword")
    private HashMap<String, String> reset(@RequestBody User user) {

        if (user.getIdUser() != null && (user.getCurrentSession() == null || !user.getCurrentSession().equals(""))) {
            User dataUser = userRepository.findByIdUser(user.getIdUser());
            if (dataUser != null) {
                if (validateRules(user.getPassword())) {
                    dataUser.setPassword( new Encoding().crypt(user.getPassword()));
                    dataUser.setCurrentSession(null);
                    dataUser.setAccessAttempts(0);
                    dataUser.setRequiresChangingPassword(0);
                    dataUser.setIdStatusUser(1L);
                    dataUser.setLastPasswordChangeDate(new Date());
                    dataUser.setLastDateOfEntry(new Date());
                    userRepository.save(dataUser);
                    response.put("code", "0");
                    response.put("message", OK);
                    return response;
                }
                response.put("code", "1");
                response.put("message", fails);
                return response;
            }
            response.put("code", "1");
            response.put("message", errorClient);
            return response;
        }
        response.put("code", "1");
        response.put("message", errorParameters);
        return response;
    }


    private Boolean validateRules(String dataPass) {
        if (dataPass != null) {
            Optional<Company> dataCompany = companyRepository.findById(1L);
            int mayusculasRequeridas = dataCompany.get().getPasswordAmountUppercase();
            int minusculasRequeridas = dataCompany.get().getPasswordAmountLowercase();
            int numerosRequeridos = dataCompany.get().getPasswordAmountNumber();
            int simbolosRequeridos = dataCompany.get().getPasswordAmountSpecialCharacters();
            int longitudMinima = dataCompany.get().getPasswordlength();
            int longitudMaxima = MaxCharterPassword;

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
                            System.out.println("simbolo -> OK "+simbolosEncontrados);
                            return true;
                        }
                        System.out.println("simbolo -> Fail");
                    }
                    System.out.println("numeros -> Fail");
                }
                System.out.println("minusculas -> Fail");
            }
            System.out.println("mayusculas -> Fail");
        }
        return false;
    }


}
