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
    TypeDocumentRepository typeDocumentRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PeriodSpreadsheetRepository periodSpreadsheetRepository;


    @GetMapping(path = "/typeDocument")
    private List<TypeDocument> typeDocumentList() {
        return typeDocumentRepository.findAll();
    }

    @GetMapping(path = "/department")
    private List<Department> departmentList() {
        return departmentRepository.findAll();
    }

    @GetMapping(path = "/periodSpreadsheet")
    private List<PayrollPeriod> payrollPeriodLis(){
        return periodSpreadsheetRepository.findAll();
    }
}
