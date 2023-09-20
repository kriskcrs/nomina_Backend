package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.Encoding;
import org.hibernate.loader.ast.spi.MultiKeyLoadSizingStrategy;
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

    String ok = "Se actualiza";
    String error = "La contraseña minima debe ser mayor a 5 caracteres";
    String fails = "No se puede actualizar";
    HashMap<String, String> response = new HashMap<>();

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

    @PutMapping(path = "/modifyModule/{idModule}")
    private HashMap<String, String> modifyModule(@RequestBody Module module, @PathVariable long idModule){
        if(idModule>0){
            Optional<Module> dataBranch = moduleRepository.findById(idModule);
            if(dataBranch.isPresent()){
                dataBranch.get().setName(module.getName());
                dataBranch.get().setModificationDate(new Date());
                dataBranch.get().setUserModification(module.getUserModification());
                moduleRepository.save(dataBranch.get());
                response.put("code","0");
                response.put("message","Se actualizo exitosamente");
                return  response;
            }
        }
        response.put("code","1");
        response.put("message","No se actualizo");
        return  response;
    }

    @PostMapping(path = "/createModulo")
    private HashMap<String, String> createModulo(@RequestBody Module module){
        try{
            long idModulo = moduleRepository.findAll().size();
            idModulo++;
            int idOrdenMenu = moduleRepository.findAll().size();
            idOrdenMenu++;
            module.setIdModule(idModulo);
            module.setOrderMenu(idOrdenMenu);
            module.setName(module.getName());
            module.setCreationDate(new Date());
            module.setModificationDate(null);
            module.setUserModification(null);
            moduleRepository.save(module);
            response.put("code","0");
            response.put("message","Se agrego exitosamente");
            return response;
        }catch (Exception e){
            System.out.println("Error creando roles" + e.getMessage() +" causa" +e.getCause());
            response.put("code","1");
            response.put("message","Error");
            return response;
        }


    }
    @GetMapping(path = "/location")
    private List<Location> branchList(){
        return locationRepository.findAll();
    }

    @GetMapping(path = "/encripta/{text}")
    private String encrip(@PathVariable String text){
        return new Encoding().crypt(text);
    }

}
