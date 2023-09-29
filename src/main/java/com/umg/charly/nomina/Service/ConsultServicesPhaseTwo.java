package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("v1")
@CrossOrigin

public class ConsultServicesPhaseTwo {

    @Autowired
    AbsenceRepository absenceRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    PersonRepository personRepository;


    @GetMapping(path = "/absences")
    private List<Absence> AbsenceList() {
        return absenceRepository.findAll();
    }

    @GetMapping(path = "/positions")
    private List<Position> PositionList() {
        return positionRepository.findAll();
    }

    @GetMapping(path = "/persons")
    private List<Person> PersonList() {
        return personRepository.findAll();
    }
}
