package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class ConsultServicesPhase2 {
    @Autowired
    TypeDocumentRepository typeDocumentRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    PayrollPeriodRepository payrollPeriodRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MaritalStatusRepository maritalStatusRepository;
    @Autowired
    PersonDocumentRepository personDocumentRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AbsenceRepository absenceRepository;

    @Autowired
    BankRepository bankRepository;
    @Autowired
    private StatusEmployeeRepository statusEmployeeRepository;

    @GetMapping(path = "/typeDocument")
    private List<TypeDocument> typeDocumentList() {
        return typeDocumentRepository.findAll();
    }

    @GetMapping(path = "/department")
    private List<Department> departmentList() {
        return departmentRepository.findAll();
    }

    @GetMapping(path = "/employee")
    private List<Employee> employeeList(){
        return employeeRepository.findAll();
    }

    @GetMapping(path = "/maritalStatus")
    private List<MaritalStatus> maritalStatusList(){
        return maritalStatusRepository.findAll();
    }

    @GetMapping(path = "/personDocument")
    private List<PersonDocument> personDocumentList(){
        return personDocumentRepository.findAll();
    }

    @GetMapping(path = "/positions")
    private List<Position> PositionList() {
        return positionRepository.findAll();
    }

    @GetMapping(path = "/persons")
    private List<Person> PersonList() {return personRepository.findAll();}

    @GetMapping(path = "/absences")
    private List<Absence> AbsenceList() {return absenceRepository.findAll();}

    @GetMapping(path = "/bank")
    private List<Bank> bankList(){return bankRepository.findAll();
    }

    @GetMapping(path = "/statusEmployee")
    private List<StatusEmployee> statusEmployeeList(){
        return statusEmployeeRepository.findAll();
    }





}


