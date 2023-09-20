package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.Gender;
import com.umg.charly.nomina.Repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("v1")

public class GenderService {


    @Autowired
    private GenderRepository genderRepository;

    @GetMapping(path = "/gender")
    public List<Gender> genderList() {List<Gender> genders = genderRepository.findAll(); return genders;}


}
