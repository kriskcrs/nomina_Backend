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

    @Value("${image.upload.path}")
    private String uploadPath;

    @PostMapping(path = "/saveImage")
    private String test(@RequestParam("file") MultipartFile file,
        @RequestParam("idUser") String idUser) {
        try {
            User user = userRepository.findByIdUser(idUser);
            saveImageDB(file,user);
            saveImageFile(file, user);
            return "ok";
        } catch (Exception e) {
            System.out.println(e.getMessage()+ "\n" + e.getCause());
            return "Error";
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

    public void saveImageFile(MultipartFile file, User user) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Archivo no válido");
        }

        // Obtener el nombre del archivo original
        String originalFileName = file.getOriginalFilename();

        // Crear el directorio de carga si no existe
        File uploadDirectory = new File(uploadPath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        // Guardar la imagen en el directorio de carga
        Path filePath = Paths.get(uploadPath, originalFileName);
        file.transferTo(filePath.toFile());

        // Renombrar la imagen si es necesario
        Path oldFilePath = Paths.get(uploadPath, originalFileName);

        String nuevoNombre = user.getIdUser()+".jpg"; // Nuevo nombre deseado
        Path newFilePath = Paths.get(uploadPath, nuevoNombre);

        File oldFile = oldFilePath.toFile();
        File newFile = newFilePath.toFile();

        if (oldFile.exists()) {
            boolean renombrado = oldFile.renameTo(newFile);
            if (renombrado) {
                System.out.println("Se renombro la imagen con extio");
            } else {
                System.out.println("No se puedo renombrar la imagen");
            }
        }
    }


}
