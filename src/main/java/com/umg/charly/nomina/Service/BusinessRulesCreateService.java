package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class BusinessRulesCreateService {

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


    @PostMapping(path = "/bussinesRulesModify")
    private HashMap<String, String> modify(@RequestBody Company company){
        if(company.getIdCompany() != null ){
            if(company.getPasswordlength()>5){
                company.setModificationDate(new Date());
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



    @PostMapping(path = "/createMenu")
    private Menu createMenu(@RequestBody Menu menu){
        return menuRepository.save(menu);
    }

    @PostMapping(path = "/createOption")
    private Option createMenu(@RequestBody Option option){
        return optionRepository.save(option);
    }

    @PostMapping(path = "/createModule")
    private com.umg.charly.nomina.Entity.Module createMenu(@RequestBody Module module){
        return moduleRepository.save(module);
    }



    @PostMapping(path = "/createRol")
    private HashMap<String, String> createRole(@RequestBody Role role){
        try{
            long idRol = roleRepository.findAll().size();
            idRol++;
            role.setIdRole(idRol);
            role.setCreationDate(new Date());
            role.setModificationDate(null);
            role.setUserModification(null);
            roleRepository.save(role);
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

    @PutMapping(path = "/modifyRol")
    private HashMap<String, String> modifyRole(@RequestBody Role role){
        try{
            role.setModificationDate(new Date());
            roleRepository.save(role);
            response.put("code","0");
            response.put("message","Se actualizo exitosamente");
            return response;
        }catch (Exception e){
            System.out.println("Error actualizando roles" + e.getMessage() +" causa" +e.getCause());
            response.put("code","1");
            response.put("message","Error");
            return response;
        }


    }


    @PostMapping(path = "/createLocation")
    private HashMap<String, String> createBranch(@RequestBody Location location){
        try {
            long count = locationRepository.findAll().size();
            count++;
            location.setIdBranch(count);
            location.setCreationDate(new Date());
            location.setModificationDate(null);
            location.setUserModification(null);
            locationRepository.save(location);
            response.put("code","0");
            response.put("message","Se agrego exitosamente");
            return  response;
        }catch (Exception e){
            System.out.println("Error menssage "+e.getMessage() + " causa " +e.getCause());
            response.put("code","1");
            response.put("message","No se agrego");
            return  response;
        }
    }

    @PutMapping(path = "/modifyLocation")
    private HashMap<String, String> modifyBranch(@RequestBody Location location){
        System.out.println(location.getIdBranch());
        if(location.getIdBranch()>0){

            Optional<Location> dataBranch = locationRepository.findById(location.getIdBranch());
            if(dataBranch.isPresent()){
                location.setIdBranch(dataBranch.get().getIdBranch());
                location.setModificationDate(new Date());
                locationRepository.save(location);
                response.put("code","0");
                response.put("message","Se actualizo exitosamente");
                return  response;
            }
        }
        response.put("code","1");
        response.put("message","No se actualizo");
        return  response;
    }



}
