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

    @Autowired
    TypeDocumentRepository typeDocumentRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PeriodSpreadsheetRepository periodSpreadsheetRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    PersonRepository personRepository;

    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
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
            absenceRepository.save(absence);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error creando la inasistencia" + e.getMessage() + " causa" + e.getCause());
            System.out.println( e.getMessage() + " causa" + e.getCause());
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
            System.out.println( e.getMessage() + " causa" + e.getCause());
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
            System.out.println( e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //Puesto
    @PostMapping(path = "/createPosition")
    private HashMap<String, String> createPosition(@RequestBody Position position) {
        try {
            long idPosition = positionRepository.findAll().size();
            idPosition++;
            position.setIdPosition(idPosition);
            position.setCreationDate(new Date());
            positionRepository.save(position);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error creando el puesto" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updatePosition/{id}")
    private HashMap<String, String> updatePosition(@RequestBody Position position, @PathVariable long id) {
        try {
            Position positionFind = positionRepository.findByIdPosition(id);
            positionFind.setModificationDate(new Date());
            positionFind.setName(position.getName());
            positionFind.setIdDepartment(position.getIdDepartment());
            positionFind.setUserModification(position.getUserModification());
            positionRepository.save(positionFind);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deletePosition/{id}")
    private HashMap<String, String> deletePosition(@PathVariable long id) {
        try {
            positionRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //Persona
    @PostMapping(path = "/createPerson")
    private HashMap<String, String> createPerson(@RequestBody Person person) {
        try {
            long idPerson = personRepository.findAll().size();
            idPerson++;
            person.setIdPerson(idPerson);
            person.setCreationDate(new Date());
            personRepository.save(person);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error creando la persona" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updatePerson/{id}")
    private HashMap<String, String> updatePerson(@RequestBody Person person, @PathVariable long id) {
        try {
            Person personFind = personRepository.findByIdPerson(id);
            personFind.setModificationDate(new Date());
            personFind.setName(person.getName());
            personFind.setName(person.getLastname());
            personFind.setIdGender(person.getIdGender());
            personFind.setAddress(person.getAddress());
            personFind.setPhone(person.getPhone());
            personFind.setEmail(person.getEmail());
            personFind.setIdCivilstatus(person.getIdCivilstatus());
            personFind.setUserModification(person.getUserModification());
            personRepository.save(personFind);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deletePerson/{id}")
    private HashMap<String, String> deletePerson(@PathVariable long id) {
        try {
            personRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    @PostMapping(path = "/createTypeDocument")
    private HashMap<String, String> createTypeDocument(@RequestBody TypeDocument typeDocument) {
        try {
            long idTypeDocument = typeDocumentRepository.findAll().size();
            idTypeDocument++;
            typeDocument.setIdTypeDocument(idTypeDocument);
            typeDocument.setCreationDate(new Date());
            typeDocumentRepository.save(typeDocument);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error creando el tipo de documento" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateTypeDocument/{id}")
    private HashMap<String, String> updateTypeDocument(@RequestBody Absence absence, @PathVariable long id) {
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

    @DeleteMapping(path = "/deleteTypeDocument/{id}")
    private HashMap<String, String> deleteTypeDocument(@PathVariable long id) {
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
