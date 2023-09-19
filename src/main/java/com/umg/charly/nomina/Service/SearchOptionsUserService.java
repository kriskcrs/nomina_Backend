package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.Menu;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Entity.Option;
import com.umg.charly.nomina.Entity.RoleOption;
import com.umg.charly.nomina.Entity.UserRole;
import com.umg.charly.nomina.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1")
public class SearchOptionsUserService {

    HashMap<String, List> response = new HashMap<String, List>();
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private RoleOptionRepository roleOptionRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping(path = "/search/{idUser}")
    private HashMap<String, List> test(@PathVariable String idUser){
        //consultas
        UserRole userRole = userRoleRepository.findByIdUser(idUser);
        List<RoleOption> roleOptionList = roleOptionRepository.findAll();
        List<Option> optionList = optionRepository.findAll();
        List<Menu> menuList = menuRepository.findAll();
        List<Module> moduleList = moduleRepository.findAll();


        //Objetos para devolver
        List<RoleOption> roleOptionUserList = new ArrayList<>();
        List<Option> optionUserList = new ArrayList<>();
        List<Menu> menuUserList = new ArrayList<>();
        List<Module> moduleUserList = new ArrayList<>();

        boolean fistMenu = true;
        boolean firstModule = true;

        //Busqueda de opciones en rol de usuario
        for (RoleOption roleOption : roleOptionList){
            if(roleOption.getIdPK().getIdRole() == userRole.getIdRole()){
                roleOptionUserList.add(roleOption);
                //Busqueda de descripcion de cada opcion
                for(Option option : optionList){
                    if(option.getIdOption() == roleOption.getIdPK().getIdOption()){
                        optionUserList.add(option);
                        //Busqueda de descripcion de cada menu de cada rol
                        for(Menu menu : menuList){
                            if(menu.getIdMenu() == option.getIdMenu()){
                                if(fistMenu){
                                    menuUserList.add(menu);
                                    fistMenu = false;
                                }else{
                                    //filtro para no sobre escribri el mismo menu varias veces segun las opciones del usuario
                                    for(Menu menuAdded : menuUserList){
                                        if(menuAdded.getIdMenu() != option.getIdMenu()){
                                            menuUserList.add(menu);
                                            break;
                                        }
                                    }
                                }
                                for(Module module : moduleList){
                                    if(module.getIdModule() == menu.getIdModulo()){
                                        if(firstModule){
                                            moduleUserList.add(module);
                                            firstModule = false;
                                        }else{
                                            //filtro para no sobre escribri el mismo modulo varias veces segun los menu del usuario
                                            for(Module moduleAdded : moduleUserList){
                                                if(moduleAdded.getIdModule() != menu.getIdModulo()){
                                                    moduleUserList.add(module);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //response
        response.put("roleOption", roleOptionUserList);
        response.put("option", optionUserList);
        response.put("menu", menuUserList);
        response.put("module", moduleUserList);



        return response;
    }
}
