package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Company;
import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Repository.CompanyRepository;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Tools.Encoding;
import com.umg.charly.nomina.Tools.EncodingUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.SimpleTimeZone;

@RestController
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
    private CompanyRepository companyRepository;

    @PostMapping(path = "/login")
    private HashMap<String, String> login(@RequestBody User user){
        //System.out.println(user.getIdUser() + user.getPassword());

        //encoding password
        user.setPassword(new Encoding().crypt(user.getPassword()));
        //System.out.println(user.getPassword());

        //Validate user exist
        User userExist = userRepository.findByIdUser(user.getIdUser());
        if(userExist != null){
            //validate user and password
            User userLogin = userRepository.findByIdUserAndPassword(user.getIdUser(), user.getPassword());

            if(userLogin != null) {
                //validate user inactive
                if(userLogin.getIdStatusUser() != 1){
                    response.put("code", "1");
                    response.put("message", StatusUser);
                    return response;
                }else {
                    //Valide new user -> first login
                    if(userLogin.getLastDateOfEntry() == null || userLogin.getLastDateOfEntry().equals("")){
                        response.put("code", "2");
                        response.put("message", FirstLogin);
                        return response;
                    }else{
                        //validate require change password
                        if(userLogin.getRequiresChangingPassword() != 0){
                            response.put("code", "3");
                            response.put("message", RequiredChangePassword);
                            return response;
                        }else{
                            //validate session
                            if(userLogin.getCurrentSession().equals("") || userLogin.getCurrentSession() == null){
                                //Login OK
                                //SesionID, Access Attemps = 0
                                userLogin.setCurrentSession(String.valueOf(new EncodingUUID().SessionManager()));
                                userLogin.setAccessAttempts(0);
                                userLogin.setLastDateOfEntry(new Date());

                                //response
                                response.put("code","0");
                                response.put("message", "ok");
                                response.put("session", userLogin.getCurrentSession());
                                response.put("user", userLogin.getIdUser());
                                userRepository.save(userLogin);
                                return response;
                            }else{
                                response.put("code", "1");
                                response.put("message", CurrentSession);
                                return response;
                            }
                        }
                    }
                }
            }else{
                FailedLogin(userExist);
                response.put("code", "1");
                response.put("message", FailedLogin);
                return response;
            }
        }else{
            response.put("code", "1");
            response.put("message", FailedLogin);
            return response;
        }
    }

    private void FailedLogin(User user){
        //check rule
        Company company = companyRepository.findByIdcompany(1);
        //System.out.println(company.getPasswordAmountAttemptsBeforeBlocking());
        //System.out.println(user.getAccessAttempts());

        user.setAccessAttempts(user.getAccessAttempts() + 1);
        if(user.getAccessAttempts() >= company.getPasswordAmountAttemptsBeforeBlocking()){
            user.setIdStatusUser(Long.valueOf(2));
        }

        userRepository.save(user);
    }

}
