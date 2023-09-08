package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Company;
import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Entity.UserRole;
import com.umg.charly.nomina.Repository.CompanyRepository;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Repository.UserRoleRepository;
import com.umg.charly.nomina.Tools.Encoding;
import com.umg.charly.nomina.Tools.PasswordGenerator;
import com.umg.charly.nomina.Tools.SendPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    String noCumple = "No cumple las condiciones";
    String noCliente = "Cliente no existe";
    String parametrosError = "Parametros incorrectos";

    @GetMapping(path = "/user")
    private List<User> userList() {
            return userRepository.findAll();
    }


    @GetMapping(path = "/userRole")
    private List<UserRole> userRoleList() {
            return userRoleRepository.findAll();
    }

    @PostMapping(path = "/resetPassword")
    private String reset(@RequestBody User user) {
        if (user.getIdUser() != null) {
            User dataUser = userRepository.findByIdUser(user.getIdUser());
            if (dataUser != null) {
                if (validateRules(user.getPassword())) {
                    String cript = new Encoding().crypt(user.getPassword());
                    cript = new Encoding().crypt(cript);
                    dataUser.setPassword(cript);
                    dataUser.setCurrentSession(null);
                    dataUser.setAccessAttempts(0);
                    userRepository.save(dataUser);
                    return "Exitoso";
                }
                return noCumple;
            }
            return noCliente;
        }
        return parametrosError;
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

    @PostMapping(path = "/createUser")
    public String createUser(@RequestBody User user) {
        try {
            User existingUser = userRepository.findByIdUser(user.getIdUser());
            if (existingUser != null) {
                String errorMessage = "Error: El correo '" + user.getIdUser() + "' ya se encuentra registrado, prueba con otro.";
                System.err.println(errorMessage);
                return errorMessage;
            }
            String generatedPassword = PasswordGenerator.generatePassword(8, 2, 3, 3);
            user.setPassword(generatedPassword);
            // Va a encriptar la contraseña :D
            Encoding encoder = new Encoding();
            String encryptedPassword = encoder.crypt(generatedPassword);
            user.setPassword(encryptedPassword);
            SendPassword.sendPasswordByEmail(user.getEmail(), generatedPassword);
            userRepository.save(user);
            String Message = "Usuario creado exitosamente";
            System.out.println(Message);
            return Message;
        } catch (Exception e) {
            String errorMessage = "Error al crear el usuario: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            return errorMessage;
        }
    }

}

