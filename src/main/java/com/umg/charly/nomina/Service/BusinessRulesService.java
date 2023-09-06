package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    @GetMapping(path = "/bussinesRules")
    private List<Company> rules(){
      try {
          return companyRepository.findAll();
      }catch (Exception e){
          System.out.println(e.getMessage());
          return null;
      }
    }

    @PostMapping(path = "/bussinesRulesModify")
    private String modify(@RequestBody Company company){
        if(company.getIdcompany() != null){
            if(company.getPasswordlength()>5){
                companyRepository.save(company);
                return "Se actualiza";
            }
            return "La contraseña minima debe ser mayor a 5 caracters";
        }
        return "No se puede actualizar";
    }

    @GetMapping(path = "/questionsUser/{user}")
    private List<UserQuestions> userQuest(@PathVariable String user){
        System.out.println(user);
        if(!userQuestionsRepository.findByIdUser(user).isEmpty()){
            return userQuestionsRepository.findByIdUser(user);
        }
        return null;
    }

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

}
