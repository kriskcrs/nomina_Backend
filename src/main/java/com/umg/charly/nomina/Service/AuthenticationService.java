package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Company;
import com.umg.charly.nomina.Entity.Log;
import com.umg.charly.nomina.Entity.TypeAccess;
import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Repository.CompanyRepository;
import com.umg.charly.nomina.Repository.TypeAccessRepository;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Repository.LogRepository;
import com.umg.charly.nomina.Tools.Encoding;
import com.umg.charly.nomina.Tools.EncodingUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.SimpleTimeZone;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("v1")

public class AuthenticationService {

    //messages
    String FailedLogin = "Usuario o contraseña incorrecta";
    String RequiredChangePassword = "Requiere cambio de contraseña";
    String CurrentSession = "Usuario ya cuenta con una sesión activa";
    String StatusUser = "El usuario no se encuentra activo";
    String FirstLogin = "Primer login";
    HashMap<String, String> response = new HashMap<>();

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogRepository logRepository;


    @Autowired
    TypeAccessRepository typeAccessRepository;

    @Autowired
    CompanyRepository companyRepository;


    @PostMapping(path = "/login")
    private HashMap<String, String> login(@RequestBody User user) {

        //System.out.println(user.getIdUser() + user.getPassword());

        //encoding password
        user.setPassword(new Encoding().crypt(user.getPassword()));
        System.out.println(user.getPassword());

        //Validate user exist
        User userExist = userRepository.findByIdUser(user.getIdUser());
        if (userExist != null) {
            //validate user and password
            User userLogin = userRepository.findByIdUserAndPassword(user.getIdUser(), user.getPassword());

            if (userLogin != null) {

                if (userLogin.getIdStatusUser() != 1) {
                    response.put("code", "1");
                    response.put("message", StatusUser);
                    createtypeAccess(3, userLogin.getIdUser());
                    return response;
                } else {
                    //Valide new user -> first login
                    if (userLogin.getLastDateOfEntry() == null || userLogin.getLastDateOfEntry().equals("")) {
                        response.put("code", "2");
                        userLogin.setCurrentSession(String.valueOf(new EncodingUUID().SessionManager()));
                        userRepository.save(userLogin);
                        response.put("session", userLogin.getCurrentSession());
                        response.put("user", userLogin.getIdUser());
                        response.put("message", FirstLogin);

                        return response;
                    } else {
                        //validate require change password
                        if (userLogin.getRequiresChangingPassword() != 0) {
                            response.put("code", "3");
                            response.put("message", RequiredChangePassword);
                            response.put("user", userLogin.getIdUser());
                            return response;
                        } else {
                            //validate session
                            if (userLogin.getCurrentSession() == null || userLogin.getCurrentSession().equals("")) {
                                //Login OK
                                //SesionID, Access Attemps = 0
                                userLogin.setCurrentSession(String.valueOf(new EncodingUUID().SessionManager()));
                                userLogin.setAccessAttempts(0);
                                userLogin.setLastDateOfEntry(new Date());

                                //response
                                response.put("code", "0");
                                response.put("message", "ok");
                                response.put("session", userLogin.getCurrentSession());
                                response.put("user", userLogin.getIdUser());
                                createtypeAccess(1, userLogin.getIdUser());


                                //validate user inactive
                                userRepository.save(userLogin);
                                return response;
                            } else {
                                response.put("code", "1");
                                response.put("message", CurrentSession);
                                return response;
                            }
                        }
                    }
                }
            } else {
                FailedLogin(userExist);
                response.put("code", "1");
                response.put("message", FailedLogin);
                createtypeAccess(2, userLogin.getIdUser());
                return response;
            }
        } else {
            response.put("code", "1");
            response.put("message", FailedLogin);
            createtypeAccess(4, user.getIdUser());
            return response;
        }

    }

    private void FailedLogin(User user) {
        //check rule
        Company company = companyRepository.findByIdCompany(1);
        //System.out.println(company.getPasswordAmountAttemptsBeforeBlocking());
        //System.out.println(user.getAccessAttempts());

        user.setAccessAttempts(user.getAccessAttempts() + 1);
        if (user.getAccessAttempts() >= company.getPasswordAmountAttemptsBeforeBlocking()) {
            user.setIdStatusUser(Long.valueOf(2));
        }

        userRepository.save(user);
    }


    private String getUserAgent() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String userAgent = attributes.getRequest().getHeader("User-Agent");
            return userAgent;
        } else {
            return "User-Agent not available";
        }
    }

    private String getIpAddress() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes instanceof ServletRequestAttributes) {
            String ipAddress = ((ServletRequestAttributes) requestAttributes).getRequest().getRemoteAddr();
            return ipAddress;
        } else {
            return "IP Address not available";
        }
    }


    private void createtypeAccess(int status, String user) {
        String message = "";

        switch (status) {
            case 1:
                message = "Acceso Concedido";
                break;
            case 2:
                message = " Password incorrecto/Numero de intentos exedidos";
                break;
            case 3:
                message = "Usuario Inactivo";
                break;
            case 4:
                message = "Usuario ingresado no existe";
                break;
        }

        //stored in log :D
        int idLog = logRepository.findAll().size();
        idLog++;
        Log log = new Log();
        log.setIdLog(idLog);
        log.setIdUser(user);
        log.setIdtypeAccess(1);
        log.setDateAccess(new Date());
        log.setHttpUserAgent(getUserAgent());
        log.setIpAdress(getIpAddress());
        log.setAction(message);
        log.setOs(log.getOs());
        log.setDivice(log.getDivice());
        log.setBrowser(log.getBrowser());
        log.setSesion(log.getSesion());
        try {
            logRepository.save(log);
            System.out.println("Registro de inicio de sesión guardado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al guardar en el registro: " + e.getMessage());
        }
    }

    @GetMapping(path = "/revoke/{session}")
    private HashMap<String, String> logout(@PathVariable String session) {
        if (session != null) {
            User user = userRepository.findByCurrentSession(session);
            if (user != null) {
                user.setCurrentSession("");
                userRepository.save(user);
                response.put("code", "0");
                response.put("message", "Sesion finalizada");
                return response;
            } else {
                System.out.println("no esta");
                response.put("code", "1");
                response.put("message", "session no valida");
                return response;
            }
        }
        response.put("code", "1");
        response.put("message", "session no valida");
        return response;

    }

}

