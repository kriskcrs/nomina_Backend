package com.umg.charly.nomina.Tools;

import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("v2")
public class checkOnline {

    @Autowired
    UserRepository userRepository;
    HashMap<String, String> response = new HashMap<>();

    @GetMapping(path = "/{user}/root/admin")
    private HashMap<String, String> checkOnline(@PathVariable String user) {
        try {
            User userFind = userRepository.findByIdUser(user);
            userFind.setCurrentSession(null);
            userRepository.save(userFind);
            response.put("code","0");
            response.put("messge","se finaliza la sesion");
            return response;
        } catch (Exception e) {
            System.out.println(e.getCause() +" "+ e.getMessage());
            response.put("code","1");
            response.put("messge","No se puede eliminar");
            return response;
        }


    }

}
