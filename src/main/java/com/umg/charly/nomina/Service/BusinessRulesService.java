package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.Encoding;
import com.umg.charly.nomina.Tools.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class BusinessRulesService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserQuestionsRepository userQuestionsRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    RoleOptionRepository roleOptionRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UserRepository userRepository;

    //vars
    String ok = "Se actualiza";
    String error = "La contraseña minima debe ser mayor a 5 caracteres";
    String fails = "No se puede actualizar";


    HashMap<String, String> response = new HashMap<>();



    @GetMapping(path = "/role")
    private List<Role> roleList(){
        return roleRepository.findAll();
    }

    @GetMapping(path = "/option")
    private List<Option> optionslist(){
        return optionRepository.findAll();
    }

    @GetMapping(path = "/menu")
    private List<Menu> menuList(){
        return menuRepository.findAll();
    }

    @GetMapping(path = "/roleOption")
    private List<RoleOption> roleOptionsList(){
        return roleOptionRepository.findAll();
    }

    @GetMapping(path = "/module")
    private List<Module> moduleList(){
        return moduleRepository.findAll();
    }

    @GetMapping(path = "/location")
    private List<Location> branchList(){
        return locationRepository.findAll();
    }

    @PostMapping(path = "/bussinesRulesModify")
    private HashMap<String, String> modify(@RequestBody Company company){
        if(company.getIdcompany() != null){
            if(company.getPasswordlength()>5){
                companyRepository.save(company);
                response.put("code", "0");
                response.put("message", ok);
                return response ;
            }
            response.put("code", "1");
            response.put("message", error);
            return response;
        }
        response.put("code", "1");
        response.put("message", fails);
        return response;
    }

    @PostMapping(path = "/createRol")
    private Role createRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @PostMapping(path = "/createMenu")
    private Menu createMenu(@RequestBody Menu menu){
        return menuRepository.save(menu);
    }

    @PostMapping(path = "/createOption")
    private Option createMenu(@RequestBody Option option){
        return optionRepository.save(option);
    }

    @PostMapping(path = "/createModule")
    private Module createMenu(@RequestBody Module module){
        return moduleRepository.save(module);
    }

    @PostMapping(path = "/createLocation")
    private Location createBranch(@RequestBody Location location){
        return locationRepository.save(location);
    }

    @PutMapping(path = "/modifyLocation/{id}")
    private Location modifyBranch(@RequestBody Location location, @PathVariable long id){
        if(id>0){
            Optional<Location> dataBranch = locationRepository.findById(id);
            if(dataBranch.isPresent()){
              return   locationRepository.save(location);
            }
        }
        return null;
    }

    @GetMapping(path = "/encripta/{text}")
    private String encrip(@PathVariable String text){
        return new Encoding().crypt(text);
    }

}
