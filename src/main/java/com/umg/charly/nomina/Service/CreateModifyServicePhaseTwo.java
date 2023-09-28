package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class CreateModifyServicePhaseTwo {
    @Autowired
    AbsenceRepository absenceRepository;

    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
    String error = "La contraseña minima debe ser mayor a 5 caracteres";
    String failsU = "Hubo un problema al actualizar";
    String failsC = "Hubo un problema al crear";
    String delete = "El registro fue eliminado exitosamente";
    String delelteE = "El registro tiene mas dependencias no puede ser borrado";
    HashMap<String, String> response = new HashMap<>();

    //Inasistencia
    @PostMapping(path = "/createAbsence")
    private HashMap<String, String> createAbsence(@RequestBody Absence absence) {
        try {
            long idAbsence = absenceRepository.findAll().size();
            idAbsence++;
            absence.setIdAbsence(idAbsence);
            absence.setCreateDate(new Date());
            absence.setModificationDate(null);
            absence.setUserModification(null);
            absenceRepository.save(absence);
            response.put("code", "0");
            response.put("message", "Se agrego exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error creando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }



}
