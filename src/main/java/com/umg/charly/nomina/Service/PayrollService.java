package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.PayrollHeader;
import com.umg.charly.nomina.Repository.PayrollHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class PayrollService {

    @Autowired
    PayrollHeaderRepository payrollHeaderRepository;

    @GetMapping(path = "/payrollHeaders")
    private List<PayrollHeader> payrollHeaders(){
        return payrollHeaderRepository.findAll();
    }


}
