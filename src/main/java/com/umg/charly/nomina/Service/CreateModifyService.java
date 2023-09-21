package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class CreateModifyService {

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


    //vars
    String ok = "Se actualiza";
    String error = "La contraseña minima debe ser mayor a 5 caracteres";
    String fails = "No se puede actualizar";
    HashMap<String, String> response = new HashMap<>();

    @PostMapping(path = "/createRoleOption")
    private HashMap<String, String> createRoleOption(@RequestBody RoleOption roleOptionCreate){
        System.out.println(roleOptionCreate);
        List<RoleOption> roleOptionExistList = roleOptionRepository.findAll();
        boolean roleOptionAlreadyExist = false;

        for(RoleOption roleOptionExist : roleOptionExistList){
            if(roleOptionExist.getIdPK().getIdRole() == roleOptionCreate.getIdPK().getIdRole() &&
                    roleOptionExist.getIdPK().getIdOption() == roleOptionCreate.getIdPK().getIdOption()){
                roleOptionAlreadyExist = true;
                break;
            }
        }

        if(roleOptionAlreadyExist){
            response.put("code", "1");
            response.put("message", "La asignacion de opcion-rol ya existe");
            return response;
        }else {
            roleOptionCreate.setCreationDate(new Date());

            roleOptionRepository.save(roleOptionCreate);
            response.put("code", "0");
            response.put("message", "Asignacion rol-opcion creado satisfactoriamente");
            return response;
        }
    }

    @PutMapping(path = "/modifyRoleOption")
    private HashMap<String, String> modifyRoleOption(@RequestBody RoleOption roleOptionModify){
        roleOptionModify.setModificationDate(new Date());
        roleOptionRepository.save(roleOptionModify);
        response.put("code", "0");
        response.put("message", ok);

        return response;
    }

    @PutMapping(path = "/updateCompany")
    private HashMap<String, String> modifyCompany(@RequestBody Company company) {
        if (company.getIdCompany() != null) {
            if (company.getPasswordlength() > 5) {
                company.setModificationDate(new Date());
                companyRepository.save(company);
                response.put("code", "0");
                response.put("message", ok);
                return response;
            }
            response.put("code", "1");
            response.put("message", error);
            return response;
        }
        response.put("code", "1");
        response.put("message", fails);
        return response;
    }

    @PostMapping(path = "/createCompany")
    private HashMap<String, String> createCompany(@RequestBody Company company) {

        if (company.getPasswordlength() > 5) {
            long idCompany = companyRepository.findAll().size();
            idCompany++;
            company.setIdCompany(idCompany);
            company.setCreationDate(new Date());
            companyRepository.save(company);
            response.put("code", "0");
            response.put("message", ok);
            return response;
        }
        response.put("code", "1");
        response.put("message", error);
        return response;

    }


    @PutMapping(path = "/updateStatusUser")
    private HashMap<String, String> modifyStatusUser(@RequestBody StatusUser statusUser) {
        if (statusUser.getIdStatusUser() != null) {
            statusUser.setModificationDate(new Date());
            statusUserRepository.save(statusUser);
            response.put("code", "0");
            response.put("message", ok);
            return response;
        }
        response.put("code", "1");
        response.put("message", fails);
        return response;
    }

    @PostMapping(path = "/createStatusUser")
    private HashMap<String, String> createStatusUser(@RequestBody StatusUser statusUser) {
        long idStatusUser = statusUserRepository.findAll().size();
        idStatusUser++;
        statusUser.setIdStatusUser(idStatusUser);
        statusUser.setCreationDate(new Date());
        statusUserRepository.save(statusUser);
        response.put("code", "0");
        response.put("message", ok);
        return response;


    }

//pablo este lo tenes que cambiar para que retorne un hashmap y valide
    @PostMapping(path = "/createMenu")
    private Menu createMenu(@RequestBody Menu menu) {
        return menuRepository.save(menu);
    }

    @PostMapping(path = "/createOption")
    private HashMap<String, String> createMenu(@RequestBody Option option) {
        try {
            long idOption = optionRepository.findAll().size();
            idOption++;
            option.setIdOption(idOption);
            option.setCreationDate(new Date());
            optionRepository.save(option);
            response.put("code", "0");
            response.put("message", "Se agrego exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error creando opciones" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }


    @PostMapping(path = "/createModulo")
    private HashMap<String, String> createModulo(@RequestBody Module module) {
        try {
            long idModulo = moduleRepository.findAll().size();
            idModulo++;
            module.setIdModule(idModulo);
            module.setOrderMenu((int) idModulo);
            module.setName(module.getName());
            module.setCreationDate(new Date());
            moduleRepository.save(module);
            response.put("code", "0");
            response.put("message", "Se agrego exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error creando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }

    @PutMapping(path = "/modifyOption")
    private HashMap<String, String> modifyOption(@RequestBody Option option) {
        try {
            option.setModificationDate(new Date());
            optionRepository.save(option);
            response.put("code", "0");
            response.put("message", "Se actualizo exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error actualizando opciones" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }

    @PostMapping(path = "/createRol")
    private HashMap<String, String> createRole(@RequestBody Role role) {
        try {
            long idRol = roleRepository.findAll().size();
            idRol++;
            role.setIdRole(idRol);
            role.setCreationDate(new Date());
            role.setModificationDate(null);
            role.setUserModification(null);
            roleRepository.save(role);
            response.put("code", "0");
            response.put("message", "Se agrego exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error creando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }


    }

    @PutMapping(path = "/modifyRol")
    private HashMap<String, String> modifyRole(@RequestBody Role role) {
        try {
            role.setModificationDate(new Date());
            roleRepository.save(role);
            response.put("code", "0");
            response.put("message", "Se actualizo exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error actualizando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }


    @PostMapping(path = "/createLocation")
    private HashMap<String, String> createBranch(@RequestBody Location location) {
        try {
            long count = locationRepository.findAll().size();
            count++;
            location.setIdBranch(count);
            location.setCreationDate(new Date());
            location.setModificationDate(null);
            location.setUserModification(null);
            locationRepository.save(location);
            response.put("code", "0");
            response.put("message", "Se agrego exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error menssage " + e.getMessage() + " causa " + e.getCause());
            response.put("code", "1");
            response.put("message", "No se agrego");
            return response;
        }
    }

    @PutMapping(path = "/modifyLocation")
    private HashMap<String, String> modifyBranch(@RequestBody Location location) {
        if (location.getIdBranch() > 0) {
            Optional<Location> dataBranch = locationRepository.findById(location.getIdBranch());
            if (dataBranch.isPresent()) {
                location.setIdBranch(dataBranch.get().getIdBranch());
                location.setModificationDate(new Date());
                locationRepository.save(location);
                response.put("code", "0");
                response.put("message", "Se actualizo exitosamente");
                return response;
            }
        }
        response.put("code", "1");
        response.put("message", "No se actualizo");
        return response;
    }


    @PutMapping(path = "/modifyModule/{idModule}")
    private HashMap<String, String> modifyModule(@RequestBody Module module, @PathVariable long idModule) {
        if (idModule > 0) {
            Optional<Module> dataBranch = moduleRepository.findById(idModule);
            if (dataBranch.isPresent()) {
                dataBranch.get().setName(module.getName());
                dataBranch.get().setModificationDate(new Date());
                dataBranch.get().setUserModification(module.getUserModification());
                moduleRepository.save(dataBranch.get());
                response.put("code", "0");
                response.put("message", "Se actualizo exitosamente");
                return response;
            }
        }
        response.put("code", "1");
        response.put("message", "No se actualizo");
        return response;
    }


    @PostMapping(path = "/createGender")
    private HashMap<String, String> createGender(@RequestBody Gender gender){
        try {
            long count = genderRepository.findAll().size();
            count++;
            gender.setIdGender(count);
            gender.setCreationDate(new Date());
            genderRepository.save(gender);
            response.put("code", "0");
            response.put("message", "Se agrego exitosamente");
            return response;
        } catch (Exception e) {
            System.out.println("Error menssage " + e.getMessage() + " causa " + e.getCause());
            response.put("code", "1");
            response.put("message", "No se agrego");
            return response;
        }

    }

    @PutMapping(path = "/modifyGender")
    private HashMap<String, String> modifyGender(@RequestBody Gender gender){
        if (gender.getIdGender() != null) {
            gender.setModificationDate(new Date());
            genderRepository.save(gender);
            response.put("code", "0");
            response.put("message", ok);
            return response;
        }
        response.put("code", "1");
        response.put("message", fails);
        return response;
    }

}
