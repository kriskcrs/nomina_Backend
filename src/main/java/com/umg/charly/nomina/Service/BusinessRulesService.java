package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Company;
import com.umg.charly.nomina.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class BusinessRulesService {

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(path = "/bussinesRules")
    private List<Company> rules(){
      try {
          return companyRepository.findAll();
      }catch (Exception e){
          System.out.println(e.getMessage());
          return null;
      }
    }



}
