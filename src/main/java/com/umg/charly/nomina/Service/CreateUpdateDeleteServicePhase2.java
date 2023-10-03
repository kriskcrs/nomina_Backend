package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class CreateUpdateDeleteServicePhase2 {
    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    TypeDocumentRepository typeDocumentRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MaritalStatusRepository maritalStatusRepository;
    @Autowired
    PersonDocumentRepository personDocumentRepository;

    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
    String failsU = "Hubo un problema al actualizar";
    String failsC = "Hubo un problema al crear";
    String delete = "El registro fue eliminado exitosamente";
    String delelteE = "El registro tiene mas dependencias no puede ser borrado";
    String sesionFail = "Sesion no valida";
    HashMap<String, String> response = new HashMap<>();
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private StatusEmployeeRepository statusEmployeeRepository;

    //Inasistencia
    @PostMapping(path = "/createAbsence")
    private HashMap<String, String> createAbsence(@RequestBody Absence absence) {
        try {
            if (new KeepAlive().validateSession(UserFind(absence.getUserCreation()).getCurrentSession())) {
                long idAbsence = absenceRepository.findAll().size();
                idAbsence++;
                absence.setIdAbsence(idAbsence);
                absence.setCreateDate(new Date());
                absenceRepository.save(absence);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Error creando la inasistencia" + e.getMessage() + " causa" + e.getCause());
            System.out.println(e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateAbsence/{id}")
    private HashMap<String, String> updateAbsence(@RequestBody Absence absence, @PathVariable long id) {
        try {
            if (new KeepAlive().validateSession(UserFind(absence.getUserModification()).getCurrentSession())) {
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
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteAbsence/{id}/{user}")
    private HashMap<String, String> deleteAbsence(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                absenceRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //Puesto
    @PostMapping(path = "/createPosition")
    private HashMap<String, String> createPosition(@RequestBody Position position) {
        try {
            if (new KeepAlive().validateSession(UserFind(position.getUserCreation()).getCurrentSession())) {
                long idPosition = positionRepository.findAll().size();
                idPosition++;
                position.setIdPosition(idPosition);
                position.setCreationDate(new Date());
                positionRepository.save(position);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

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
            if (new KeepAlive().validateSession(UserFind(position.getUserModification()).getCurrentSession())) {
                Position positionFind = positionRepository.findByIdPosition(id);
                positionFind.setModificationDate(new Date());
                positionFind.setName(position.getName());
                positionFind.setIdDepartment(position.getIdDepartment());
                positionFind.setUserModification(position.getUserModification());
                positionRepository.save(positionFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deletePosition/{id}/{user}")
    private HashMap<String, String> deletePosition(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                positionRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
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
            if (new KeepAlive().validateSession(UserFind(person.getUserCreation()).getCurrentSession())) {
                long idPerson = personRepository.findAll().size();
                idPerson++;
                person.setIdPerson(idPerson);
                person.setCreationDate(new Date());
                personRepository.save(person);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
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
            if (new KeepAlive().validateSession(UserFind(person.getUserModification()).getCurrentSession())) {
                Person personFind = personRepository.findByIdPerson(id);
                personFind.setModificationDate(new Date());
                personFind.setName(person.getName());
                personFind.setName(person.getLastname());
                personFind.setIdGender(person.getIdGender());
                personFind.setAddress(person.getAddress());
                personFind.setPhone(person.getPhone());
                personFind.setEmail(person.getEmail());
                personFind.setIdMaritalStatus(person.getIdMaritalStatus());
                personFind.setUserModification(person.getUserModification());
                personRepository.save(personFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deletePerson/{id}/{user}")
    private HashMap<String, String> deletePerson(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                personRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //Tipo de documento
    @PostMapping(path = "/createTypeDocument")
    private HashMap<String, String> createTypeDocument(@RequestBody TypeDocument typeDocument) {
        try {
            if (new KeepAlive().validateSession(UserFind(typeDocument.getUserCreation()).getCurrentSession())) {
                long idTypeDocument = typeDocumentRepository.findAll().size();
                idTypeDocument++;
                typeDocument.setIdTypeDocument(idTypeDocument);
                typeDocument.setCreationDate(new Date());
                typeDocumentRepository.save(typeDocument);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Error creando el tipo de documento" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateTypeDocument/{id}")
    private HashMap<String, String> updateTypeDocument(@RequestBody TypeDocument typeDocument, @PathVariable long id) {
        try {
            if(new KeepAlive().validateSession(UserFind(typeDocument.getUserModification()).getCurrentSession())) {
                TypeDocument typeDocument1 = typeDocumentRepository.findByidTypeDocument(id);
                typeDocument1.setName(typeDocument.getName());
                typeDocument1.setModificationDate(new Date());
                typeDocument1.setUserModification(typeDocument.getUserModification());
                typeDocumentRepository.save(typeDocument1);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            }else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteTypeDocument/{id}/{user}")
    private HashMap<String, String> deleteTypeDocument(@PathVariable long id, @PathVariable String user) {
        try {
            if(new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                typeDocumentRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //Departamento
    @PostMapping(path = "/createDepartment")
    private HashMap<String, String> createDepartment(@RequestBody Department department) {
        try {
            if (new KeepAlive().validateSession(UserFind(department.getUserCreation()).getCurrentSession())) {
                long idDepartment = departmentRepository.findAll().size();
                idDepartment++;
                department.setIdDepartment(idDepartment);
                department.setCreationDate(new Date());
                departmentRepository.save(department);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Error creando el departamento" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateDepartment/{id}")
    private HashMap<String, String> updateDepartment(@RequestBody Department department, @PathVariable long id) {
        try {
            if(new KeepAlive().validateSession(UserFind(department.getUserModification()).getCurrentSession())) {
                Department department1 = departmentRepository.findByidDepartment(id);
                department1.setName(department.getName());
                department1.setIdCompany(department.getIdCompany());
                department1.setModificationDate(new Date());
                department1.setUserModification(department.getUserModification());
                departmentRepository.save(department1);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteDepartment/{id}/{user}")
    private HashMap<String, String> deleteDepartment(@PathVariable long id, @PathVariable String user) {
        try {
            if(new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                departmentRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }


    //employee
    @PostMapping(path = "/createEmployee")
    private HashMap<String, String> createEmployee(@RequestBody Employee employee) {
        try {
            if (new KeepAlive().validateSession(UserFind(employee.getUserCreation()).getCurrentSession())) {
                long id = employeeRepository.findAll().size();
                id++;
                employee.setIdEmployee(id);
                employee.setCreationDate(new Date());
                employeeRepository.save(employee);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateEmployee/{id}")
    private HashMap<String, String> updateEmployee(@RequestBody Employee employee, @PathVariable long id) {
        try {
            if (new KeepAlive().validateSession(UserFind(employee.getUserModification()).getCurrentSession())) {

                employee.setModificationDate(new Date());
                employeeRepository.save(employee);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteEmployee/{id}/{user}")
    private HashMap<String, String> deleteEmployee(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                employeeRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;

            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;
        }
    }

    //bank
    @PostMapping(path = "/createBank")
    private HashMap<String, String> createBank(@RequestBody Bank bank) {
        try {
            long id = bankRepository.findAll().size();
            id++;
            bank.setIdBank(id);
            bank.setCreationDate(new Date());
            response.put("code", "0");
            response.put("message", okC);
            bankRepository.save(bank);
            return response;

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateBank")
    private HashMap<String, String> updateBank(@RequestBody Bank bank) {
        try {
            bank.setModificationDate(new Date());
            bankRepository.save(bank);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {

            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    //status employee
    @PostMapping(path = "/createStatusEmployee")
    private HashMap<String, String> createStatusEmployee (@RequestBody StatusEmployee statusEmployee){
        try {
            long id = statusEmployeeRepository.findAll().size();
            id++;
            statusEmployee.setIdStatusEmployee(id);
            statusEmployee.setCreationDate(new Date());
            response.put("code", "0");
            response.put("message", okC);
            statusEmployeeRepository.save(statusEmployee);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateStatusEmployee")
    private HashMap<String, String> updateStatusEmployee (@RequestBody StatusEmployee statusEmployee){
        try {
            statusEmployee.setModificationDate(new Date());
            statusEmployeeRepository.save(statusEmployee);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    //estado civil
    @PostMapping(path = "/createMaritalStatus")
    private HashMap<String, String> createMaritalStatus(@RequestBody MaritalStatus maritalStatus) {
        try {
            if (new KeepAlive().validateSession(UserFind(maritalStatus.getUserCreation()).getCurrentSession())) {
                long id = maritalStatusRepository.findAll().size();
                id++;
                maritalStatus.setIdMaritalStatus(id);
                maritalStatus.setCreationDate(new Date());
                maritalStatusRepository.save(maritalStatus);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        }catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
        }
        return null;
    }

    @PutMapping(path = "/updateMaritalStatus/{id}")
    private HashMap<String, String> updateMaritalStatus(@RequestBody MaritalStatus maritalStatus, @PathVariable long id) {
        try {
            if (new KeepAlive().validateSession(UserFind(maritalStatus.getUserModification()).getCurrentSession())) {
                maritalStatus.setModificationDate(new Date());
                maritalStatusRepository.save(maritalStatus);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
        }
        return null;
    }


    @DeleteMapping(path = "/deleteMaritalStatus/{id}/{user}")
    private HashMap<String, String> deleteMaritalStatus(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                maritalStatusRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;

            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
        }
        return null;
    }


    //persona y documento



    //retorna el objeto de usuario
    private User UserFind(String user) {
        return userRepository.findByIdUser(user);
    }
}
