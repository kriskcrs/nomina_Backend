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
            absence.setProcessingDate(null);
            absence.setModificationDate(null);
            absence.setUserModification(null);
            absenceRepository.save(absence);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error creando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateAbsence/{id}")
    private HashMap<String, String> updateAbsence(@RequestBody Absence absence, @PathVariable long id) {
        try {
            Absence absenceFind = absenceRepository.findByIdAbsence(id);
            absenceFind.setModificationDate(new Date());
            absenceFind.setUserModification(absence.getUserModification());
            absenceFind.setInitialDate(absence.getInitialDate());
            absenceFind.setFinalDate(absence.getFinalDate());
            absenceFind.setReason(absence.getReason());
            absenceRepository.save(absenceFind);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteAbsence/{id}")
    private HashMap<String, String> deleteAbsence(@PathVariable long id) {
        try {
            absenceRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

}
