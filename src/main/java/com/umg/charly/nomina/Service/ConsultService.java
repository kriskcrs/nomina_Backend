package com.umg.charly.nomina.Service;



import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.Encoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class ConsultService {

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
    @Autowired
    StatusUserRepository statusUserRepository;
    @Autowired
    GenderRepository genderRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @GetMapping(path = "/userRole")
    private List<UserRole> userRoleList() {
        return userRoleRepository.findAll();
    }


    @GetMapping(path = "/bussinesRules")
    private List<Company> rules() {
        return companyRepository.findAll();
    }

    @GetMapping(path = "/role/{idRol}")
    private Role roleList(@PathVariable Long idRol){
        return roleRepository.findByIdRole(idRol);
    }

    @GetMapping(path = "/role")
    private List<Role> roleList(){
        return roleRepository.findAll();
    }

    @GetMapping(path = "/option/{idOption}")
    private Optional<Option> OptionalId(@PathVariable int idOption){
        return optionRepository.findById((long)idOption);
    }
    @GetMapping(path = "/option")
    private List<Option> optionList(){
        return optionRepository.findAll();
    }

    @GetMapping(path = "/menu")
    private List<Menu> menuList(){
        return menuRepository.findAll();
    }



    @GetMapping(path = "/menu/{idMenu}")
    private Menu menuList(@PathVariable Long idMenu){
        return menuRepository.findByIdMenu(idMenu);
    }



    @GetMapping(path = "/roleOption")
    private List<RoleOption> roleOptionsList(){
        return roleOptionRepository.findAll();
    }


    @GetMapping(path = "/module/{idModule}")
    private Module moduleList(@PathVariable Long idModule){
        return moduleRepository.findByIdModule(idModule);
    }

    @GetMapping(path = "/module")
    private List<Module> moduleLists(){
        return moduleRepository.findAll();
    }


    @GetMapping(path = "/location")
    private List<Location> branchList(){
        return locationRepository.findAll();
    }

    @GetMapping(path = "/encripta/{text}")
    private String encrip(@PathVariable String text){
        return new Encoding().crypt(text);
    }

    @GetMapping(path = "/statusUser")
    private List<StatusUser> userList(){
        return statusUserRepository.findAll() ;

    }

    @GetMapping(path = "/gender")
    public List<Gender> genderList() {List<Gender> genders = genderRepository.findAll(); return genders;}



}
