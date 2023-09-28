package com.umg.charly.nomina.Service;

import org.springframework.beans.factory.annotation.Value;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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


