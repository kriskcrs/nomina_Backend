package com.umg.charly.nomina.Service;

import com.sun.tools.jconsole.JConsoleContext;
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
    @Autowired
    UserRoleRepository userRoleRepository;


    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
    String error = "La contraseña minima debe ser mayor a 5 caracteres";
    String failsU = "Hubo un problema al actualizar";
    String failsC = "Hubo un problema al crear";
    String delete = "El registro fue eliminado exitosamente";
    String delelteE = "El registro tiene mas dependencias no puede ser borrado";
    HashMap<String, String> response = new HashMap<>();


    //company
    @PostMapping(path = "/createCompany")
    private HashMap<String, String> createCompany(@RequestBody Company company) {
        try {
            long idCompany = companyRepository.findAll().size();
            idCompany++;
            company.setIdCompany(idCompany);
            company.setCreationDate(new Date());
            companyRepository.save(company);
            response.put("code", "0");
            response.put("message", okC);
            return response;

        } catch (Exception e) {
            System.out.println(e.getCause() + e.getMessage());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateCompany/{id}")
    private HashMap<String, String> updateCompany(@RequestBody Company company, @PathVariable long id) {
        try {
            if (company.getPasswordlength() > 5) {
                Company companyFind = companyRepository.findByIdCompany(id);
                companyFind.setName(company.getName());
                companyFind.setModificationDate(new Date());
                companyFind.setUserModification(company.getUserModification());
                companyRepository.save(companyFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            }
            response.put("code", "1");
            response.put("message", error);
            return response;
        } catch (Exception e) {
            System.out.println(e.getCause() + e.getMessage());
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteCompany/{id}")
    private HashMap<String, String> deleteCompany(@PathVariable long id) {
        try {
            companyRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //location
    @PostMapping(path = "/createLocation")
    private HashMap<String, String> createLocation(@RequestBody Location location) {
        try {
            long count = locationRepository.findAll().size();
            count++;
            location.setIdLocation(count);
            location.setCreationDate(new Date());
            locationRepository.save(location);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error menssage " + e.getMessage() + " causa " + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateLocation/{id}")
    private HashMap<String, String> modifyLocation(@RequestBody Location location, @PathVariable long id) {
        try {
            Location locationFind = locationRepository.findByIdLocation(id);
            System.out.println(locationFind.getCreationDate());
            locationFind.setModificationDate(new Date());
            locationFind.setUserModification(location.getUserModification());
            locationFind.setName(location.getName());
            locationFind.setAddress(location.getAddress());
            locationRepository.save(locationFind);
            response.put("code", "0");
            response.put("message", "Se actualizo exitosamente");
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", "No se actualizo");
            return response;
        }
    }

    @DeleteMapping(path = "/deleteLocation/{id}")
    private HashMap<String, String> deleteLocation(@PathVariable long id) {
        try {
            locationRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //gender
    @PostMapping(path = "/createGender")
    private HashMap<String, String> createGender(@RequestBody Gender gender) {
        try {
            long count = genderRepository.findAll().size();
            count++;
            gender.setIdGender(count);
            gender.setCreationDate(new Date());
            genderRepository.save(gender);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error menssage " + e.getMessage() + " causa " + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }

    }

    @PutMapping(path = "/updateGender/{id}")
    private HashMap<String, String> updateGender(@RequestBody Gender gender, @PathVariable long id) {
        try {
            Gender genderFind = genderRepository.findByIdGender(id);
            genderFind.setUserModification(gender.getUserModification());
            genderFind.setModificationDate(new Date());
            genderFind.setName(gender.getName());
            genderRepository.save(genderFind);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteGender/{id}")
    private HashMap<String, String> deleteGender(@PathVariable long id) {
        try {
            genderRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }


    //statusUser

    @PostMapping(path = "/createStatusUser")
    private HashMap<String, String> createStatusUser(@RequestBody StatusUser statusUser) {
        try {
            long idStatusUser = statusUserRepository.findAll().size();
            idStatusUser++;
            statusUser.setIdStatusUser(idStatusUser);
            statusUser.setCreationDate(new Date());
            statusUserRepository.save(statusUser);
            response.put("code", "0");
            response.put("message", okC);
            return response;

        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            statusUserRepository.save(statusUser);
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }

    }

    @PutMapping(path = "/updateStatusUser/{id}")
    private HashMap<String, String> updateStatusUser(@RequestBody StatusUser statusUser, @PathVariable long id) {
        try {
            StatusUser statusUserFind = statusUserRepository.findByIdStatusUser(id);
            statusUserFind.setUserModification(statusUser.getUserModification());
            statusUserFind.setModificationDate(new Date());
            statusUserFind.setName(statusUser.getName());

            statusUserRepository.save(statusUserFind);
            response.put("code", "0");
            response.put("message", okU);
            return response;

        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }


    @DeleteMapping(path = "/deleteStatusUser/{id}")
    private HashMap<String, String> deleteStatusUser(@PathVariable long id) {
        try {
            statusUserRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }


    //menu
    @PostMapping(path = "/createMenu")
    private HashMap<String, String> createMenu(@RequestBody Menu menu) {
        try {
            long count = menuRepository.findAll().size();
            count++;
            menu.setIdMenu(count);
            menu.setCreationDate(new Date());
            menu.setModificationDate(null);
            menu.setUserModification(null);
            menuRepository.save(menu);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println("Error menssage " + e.getMessage() + " causa " + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/modifyMenu/{idMenu}")
    private HashMap<String, String> modifyMenu(@RequestBody Menu menu, @PathVariable Long idMenu) {
        if (idMenu != null) {
            Optional<Menu> dataMenu = Optional.ofNullable(menuRepository.findByIdMenu(idMenu));
            if (dataMenu.isPresent()) {
                dataMenu.get().setIdModulo(menu.getIdModulo());
                dataMenu.get().setName(menu.getName());
                dataMenu.get().setOrderMenu(menu.getOrderMenu());
                dataMenu.get().setModificationDate(new Date());
                dataMenu.get().setUserModification(menu.getUserModification());
                menuRepository.save(dataMenu.get());
                response.put("code", "0");
                response.put("message", "Se actualizo exitosamente");
                return response;
            }
        }
        response.put("code", "1");
        response.put("message", "No se actualizo");
        return response;
    }

    //option
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

    //roleOption
    @PostMapping(path = "/createRoleOption")
    private HashMap<String, String> createRoleOption(@RequestBody RoleOption roleOptionCreate) {
        System.out.println(roleOptionCreate);
        List<RoleOption> roleOptionExistList = roleOptionRepository.findAll();
        boolean roleOptionAlreadyExist = false;

        for (RoleOption roleOptionExist : roleOptionExistList) {
            if (roleOptionExist.getIdPK().getIdRole() == roleOptionCreate.getIdPK().getIdRole() &&
                    roleOptionExist.getIdPK().getIdOption() == roleOptionCreate.getIdPK().getIdOption()) {
                roleOptionAlreadyExist = true;
                break;
            }
        }

        if (roleOptionAlreadyExist) {
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        } else {
            roleOptionCreate.setCreationDate(new Date());

            roleOptionRepository.save(roleOptionCreate);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        }
    }

    @PutMapping(path = "/modifyRoleOption")
    private HashMap<String, String> modifyRoleOption(@RequestBody RoleOption roleOptionModify) {
        roleOptionModify.setModificationDate(new Date());
        roleOptionRepository.save(roleOptionModify);
        response.put("code", "0");
        response.put("message", okU);

        return response;
    }


    //modula
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

    @DeleteMapping(path = "/deleteModule/{id}")
    private HashMap<String, String> deleteModule(@PathVariable long id) {
        try {
            moduleRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //Rol
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


    //userRole
    @PutMapping(path = "/modifyuserRole")
    private HashMap<String, String> modifyuserRole(@RequestBody UserRole userrole) {
        if (userrole.getIdUser() != null) {
            userrole.setModificationDate(new Date());
            userRoleRepository.save(userrole);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        }
        response.put("code", "1");
        response.put("message", failsU);
        return response;
    }

    @PostMapping(path = "/userAsignRole")
    private HashMap<String, String> userRole(@RequestBody UserRole userrole) {
        userrole.setCreationDate(new Date());
        userRoleRepository.save(userrole);
        System.out.println("si guarda");
        response.put("code", "0");
        response.put("message", okC);
        return response;
    }

}
