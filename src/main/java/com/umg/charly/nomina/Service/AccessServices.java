package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class AccessServices {

    @Autowired
    private LogRepository logRepository;

    @GetMapping(path = "/log")
    public List<Log> logList() {List<Log> logs = logRepository.findAll(); return logs;}

}
