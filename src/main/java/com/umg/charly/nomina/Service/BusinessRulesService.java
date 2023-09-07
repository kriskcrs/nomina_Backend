package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
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


    @GetMapping(path = "/bussinesRules")
    private List<Company> rules(){
          return companyRepository.findAll();
    }

    @GetMapping(path = "/questionsUser/{user}")
    private List<UserQuestions> userQuest(@PathVariable String user){
        List<UserQuestions> userQuestions = userQuestionsRepository.findByIdUser(user);
        List<Company> company = companyRepository.findAll();
        int count = company.get(0).getPasswordAmountQuestionsValidate();
        if (!userQuestions.isEmpty()) {
            if (count <= 0) {
                return Collections.emptyList();
            } else if (count >= userQuestions.size()) {
                return userQuestions;
            } else {
                Random random = new Random();
                List<UserQuestions> randomQuestions = new ArrayList<>();
                Set<Integer> selectedIndices = new HashSet<>();
                while (randomQuestions.size() < count) {
                    int index;
                    do {
                        index = random.nextInt(userQuestions.size());
                    } while (selectedIndices.contains(index));
                    selectedIndices.add(index);
                    randomQuestions.add(userQuestions.get(index));
                }
                return randomQuestions;
            }
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
    @GetMapping(path = "/location")
    private List<Location> branchList(){
        return locationRepository.findAll();
    }


    @PostMapping(path = "/bussinesRulesModify")
    private String modify(@RequestBody Company company){
        if(company.getIdcompany() != null){
            if(company.getPasswordlength()>5){
                companyRepository.save(company);
                return "Se actualiza";
            }
            return "La contraseña minima debe ser mayor a 5 caracteres";
        }
        return "No se puede actualizar";
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
}
