package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class ImagesServices {
    @Autowired
    UserRepository userRepository;



    HashMap<String, String> respuesta = new HashMap<>();
    @PostMapping(path = "/saveImage")
    private HashMap<String, String> test(@RequestParam("file") MultipartFile file,
        @RequestParam("idUser") String idUser) {
        try {
            User user = userRepository.findByIdUser(idUser);
            saveImageDB(file,user);
            //saveImageFile(file, user);
            respuesta.put("code","0");
            return respuesta;
        } catch (Exception e) {
            System.out.println(e.getMessage()+ "\n" + e.getCause());
            respuesta.put("code","1");
            return respuesta;
        }
    }


    public void saveImageDB(MultipartFile file, User user ) throws IOException {
        byte[] data = file.getBytes();
        user.setModificationDate(new Date());
        user.setUserModification("io");
        user.setCreationDate(new Date());
        user.setPhoto(data);
        userRepository.save(user);
    }

    }


