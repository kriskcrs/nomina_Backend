package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class CreateUpdateDeleteService {

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
    String deleteE = "El registro tiene mas dependencias no puede ser borrado";
    String sesionFail = "Sesion no valida";
    HashMap<String, String> response = new HashMap<>();


    //company
    @PostMapping(path = "/createCompany")
    private HashMap<String, String> createCompany(@RequestBody Company company) {
        try {
            if (new KeepAlive().validateSession(UserFind(company.getUserCreation()).getCurrentSession())) {
                long idCompany = companyRepository.findAll().size();
                idCompany++;
                company.setIdCompany(idCompany);
                company.setCreationDate(new Date());
                companyRepository.save(company);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
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

            if (new KeepAlive().validateSession(UserFind(company.getUserModification()).getCurrentSession())) {
                if (company.getPasswordlength() > 5) {
                    company.setModificationDate(new Date());
                    companyRepository.save(company);
                    response.put("code", "0");
                    response.put("message", okU);
                    return response;
                }
                response.put("code", "1");
                response.put("message", error);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            System.out.println(e.getCause() + e.getMessage());
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteCompany/{id}/{user}")
    private HashMap<String, String> deleteCompany(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                companyRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;

        }

    }

    //location
    @PostMapping(path = "/createLocation")
    private HashMap<String, String> createLocation(@RequestBody Location location) {
        try {
            if (new KeepAlive().validateSession(UserFind(location.getUserCreation()).getCurrentSession())) {
                long count = locationRepository.findAll().size();
                count++;
                location.setIdLocation(count);
                location.setCreationDate(new Date());
                locationRepository.save(location);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

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
            if (new KeepAlive().validateSession(UserFind(location.getUserModification()).getCurrentSession())) {
                Location locationFind = locationRepository.findByIdLocation(id);
                locationFind.setModificationDate(new Date());
                locationFind.setUserModification(location.getUserModification());
                locationFind.setName(location.getName());
                locationFind.setAddress(location.getAddress());
                locationRepository.save(locationFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteLocation/{id}/{user}")
    private HashMap<String, String> deleteLocation(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                locationRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;

        }

    }

    //gender
    @PostMapping(path = "/createGender")
    private HashMap<String, String> createGender(@RequestBody Gender gender) {
        try {
            if (new KeepAlive().validateSession(UserFind(gender.getUserCreation()).getCurrentSession())) {
                long count = genderRepository.findAll().size();
                count++;
                gender.setIdGender(count);
                gender.setCreationDate(new Date());
                genderRepository.save(gender);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

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

            if (new KeepAlive().validateSession(UserFind(gender.getUserModification()).getCurrentSession())) {
                Gender genderFind = genderRepository.findByIdGender(id);
                genderFind.setUserModification(gender.getUserModification());
                genderFind.setModificationDate(new Date());
                genderFind.setName(gender.getName());
                genderRepository.save(genderFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }


        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteGender/{id}/{user}")
    private HashMap<String, String> deleteGender(@PathVariable long id, @PathVariable String user) {
        try {

            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                genderRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
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
            response.put("message", deleteE);
            return response;

        }

    }


    //userRole

    @PostMapping(path = "/createUserRole")
    private HashMap<String, String> createUserRole(@RequestBody UserRole userRole) {
        try {
            if (new KeepAlive().validateSession(UserFind(userRole.getUserCreation()).getCurrentSession())) {
                UserRole userRoleFind = userRoleRepository.findByIdUser(userRole.getIdUser());
                if (userRoleFind == null) {
                    userRole.setIdRole(userRole.getIdRole());
                    userRole.setIdUser(userRole.getIdUser());
                    userRole.setCreationDate(new Date());
                    userRoleRepository.save(userRole);
                    response.put("code", "0");
                    response.put("message", okC);
                    return response;
                } else {
                    response.put("code", "1");
                    response.put("message", "Usuario ya tiene rol no puede agregar otro");
                    return response;
                }
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }

    }


    @PutMapping(path = "/updateUserRole/{id}")
    private HashMap<String, String> updateUserRole(@RequestBody UserRole userRole, @PathVariable String id) {
        try {
            if (new KeepAlive().validateSession(UserFind(userRole.getUserModification()).getCurrentSession())) {
                UserRole userRoleFind = userRoleRepository.findByIdUser(id);
                userRoleFind.setIdRole(userRole.getIdRole());
                userRoleFind.setUserModification(userRole.getUserModification());
                userRoleFind.setModificationDate(new Date());
                userRoleRepository.save(userRoleFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;

        }

    }


    @DeleteMapping(path = "/deleteUserRole/{id}/{user}")
    private HashMap<String, String> deleteStatusUser(@PathVariable String id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                userRoleRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            response.put("code", "1");
            response.put("message", deleteE);
            return response;
        }
    }


    //menu
    @PostMapping(path = "/createMenu")
    private HashMap<String, String> createMenu(@RequestBody Menu menu) {
        try {
            if (new KeepAlive().validateSession(UserFind(menu.getUserCreation()).getCurrentSession())) {
                long id = menuRepository.findAll().size();
                id++;
                menu.setIdMenu(id);
                menu.setCreationDate(new Date());
                menuRepository.save(menu);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Error menssage " + e.getMessage() + " causa " + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateMenu/{id}")
    private HashMap<String, String> updateMenu(@RequestBody Menu menu, @PathVariable long id) {
        try {
            if(new KeepAlive().validateSession(UserFind(menu.getUserModification()).getCurrentSession())) {
                Menu MenuFind = menuRepository.findByIdMenu(id);
                MenuFind.setName(menu.getName());
                MenuFind.setOrderMenu(menu.getOrderMenu());
                MenuFind.setModificationDate(new Date());
                MenuFind.setUserModification(menu.getUserModification());
                MenuFind.setIdModulo(menu.getIdModulo());
                menuRepository.save(MenuFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }


    }

    @DeleteMapping(path = "/deleteMenu/{id}/{user}")
    private HashMap<String, String> deleteMenu(@PathVariable long id, @PathVariable String user) {
        try {
            if(new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                menuRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;

        }

    }

    //option
    @PostMapping(path = "/createOption")
    private HashMap<String, String> createMenu(@RequestBody Option option) {
        try {
            long idOption = optionRepository.findAll().size();
            idOption++;
            option.setIdOption(idOption);
            option.setCreationDate(new Date());
            option.setIdMenu(option.getIdMenu());
            optionRepository.save(option);
            response.put("code", "0");
            response.put("message", okC);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
            response.put("code", "1");
            response.put("message", failsC);
            return response;
        }
    }

    @PutMapping(path = "/updateOption/{id}")
    private HashMap<String, String> updateOption(@RequestBody Option option, @PathVariable long id) {
        try {
            Option optionlFind = optionRepository.findByIdOption(id);
            optionlFind.setModificationDate(new Date());
            optionlFind.setUserModification(option.getUserModification());
            optionlFind.setName(option.getName());
            optionlFind.setPage(option.getPage());
            optionlFind.setIdMenu(option.getIdMenu());
            optionlFind.setIdOrderMenu(option.getIdOrderMenu());
            optionRepository.save(optionlFind);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deleteOption/{id}")
    private HashMap<String, String> deleteOption(@PathVariable long id) {
        try {
            optionRepository.deleteById(id);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;
        }

    }


    //roleOption
    @PostMapping(path = "/createRoleOption")
    private HashMap<String, String> createRoleOption(@RequestBody RoleOption roleOptionCreate) {

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
            response.put("message", "Ya existe la opción asignada a este rol");
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

    @DeleteMapping(path = "/deleteRoleOption/{idRole}/{idOption}")
    private HashMap<String, String> deleteStatusUser(@PathVariable Long idRole, @PathVariable Long idOption) {
        try {
            RoleOptionPK roleOptionPK = new RoleOptionPK();
            roleOptionPK.setIdRole(idRole);
            roleOptionPK.setIdOption(idOption);

            roleOptionRepository.deleteById(roleOptionPK);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            System.out.println(e.getCause() + " " + e.getMessage());
            response.put("code", "1");
            response.put("message", deleteE);
            return response;
        }
    }


    //modula
    @PostMapping(path = "/createModulo")
    private HashMap<String, String> createModulo(@RequestBody Module module) {
        try {
            if (new KeepAlive().validateSession(UserFind(module.getUserCreation()).getCurrentSession())) {
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
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Error creando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }

    @PutMapping(path = "/modifyModule/{idModule}")
    private HashMap<String, String> modifyModule(@RequestBody Module module, @PathVariable long idModule) {
        try {
            if(new KeepAlive().validateSession(UserFind(module.getUserModification()).getCurrentSession())) {
                    Module module1 = moduleRepository.findByIdModule(idModule);
                        module1.setName(module.getName());
                        module1.setModificationDate(new Date());
                        module1.setUserModification(module.getUserModification());
                        moduleRepository.save(module1);
                        response.put("code", "0");
                        response.put("message", "Se actualizo exitosamente");
                        return response;

            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e){
            response.put("code", "1");
            response.put("message", "No se actualizo");
            return response;
        }
    }

    @DeleteMapping(path = "/deleteModule/{id}/{user}")
    private HashMap<String, String> deleteModule(@PathVariable long id, @PathVariable String user) {
        try {
            if(new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                moduleRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;

        }

    }

    //Rol
    @PostMapping(path = "/createRol")
    private HashMap<String, String> createRole(@RequestBody Role role) {
        try {
            if (new KeepAlive().validateSession(UserFind(role.getUserCreation()).getCurrentSession())) {
                long idRol = roleRepository.findAll().size();
                idRol++;
                role.setIdRole(idRol);
                role.setCreationDate(new Date());
                roleRepository.save(role);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            } else {
                response.put("code", "1");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            System.out.println("Error creando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }


    }

    @PutMapping(path = "/updateRol/{id}")
    private HashMap<String, String> updateRol(@RequestBody Role role, @PathVariable long id) {
        try {

            if (new KeepAlive().validateSession(UserFind(role.getUserModification()).getCurrentSession())) {
                Role roleFind = roleRepository.findByIdRole(id);
                roleFind.setName(role.getName());
                roleFind.setUserModification(role.getUserModification());
                roleFind.setModificationDate(new Date());
                roleRepository.save(roleFind);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "1");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Error actualizando roles" + e.getMessage() + " causa" + e.getCause());
            response.put("code", "1");
            response.put("message", "Error");
            return response;
        }
    }

    @DeleteMapping(path = "/deleteRol/{id}/{user}")
    private HashMap<String, String> deleteRol(@PathVariable long id, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                roleRepository.deleteById(id);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "1");
                response.put("message", sesionFail);
                return response;
            }

        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;

        }

    }


    private User UserFind(String user) {
        return userRepository.findByIdUser(user);
    }
}
