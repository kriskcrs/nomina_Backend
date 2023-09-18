package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.FullRol;
import com.umg.charly.nomina.Entity.Option;
import com.umg.charly.nomina.Entity.RoleOption;
import com.umg.charly.nomina.Entity.UserRole;
import com.umg.charly.nomina.Repository.*;
import org.aspectj.weaver.loadtime.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class FullRolService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleOptionRepository roleOptionRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    MenuRepository  menuRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @GetMapping (path = "/fullRol/{idUser}")
    private HashMap<String,HashMap> fullRole(@PathVariable String idUser){
        if(idUser!=null){
            UserRole usuarioRol = userRoleRepository.findByIdUser(idUser);
            List <RoleOption> roleOptions = roleOptionRepository.findAll();
            HashMap <String, String> LisRol = new HashMap<>();
            HashMap <String, HashMap> ListOption = new HashMap<>();
            int x = 0;
            for(x=0;x<roleOptions.size();x++){

                if(roleOptions.get(x).getIdPK().getIdRole().equals(usuarioRol.getIdRole())){
                    LisRol.put("idOption "+ x, String.valueOf(roleOptions.get(x).getIdPK().getIdOption()));

                }
            }
            System.out.println(LisRol);
            for(int y=0;y<LisRol.size();y++){
                String id="idOption "+y;
                System.out.println(LisRol.get("idOption "+y));

                ListOption.put(id,option(Long.valueOf(LisRol.get("idOption "+y))));
            }
            System.out.println(ListOption);
            return ListOption;

        }else{
            return null;
        }
    }

    private HashMap<String,String>  option(Long idOption){
        HashMap <String, String> DataRol = new HashMap<>();
        Optional<Option> option = optionRepository.findById(idOption);
        DataRol.put("idOption", String.valueOf(option.get().getIdOption()));
        DataRol.put("name" , option.get().getName());
        DataRol.put("idMenu" , String.valueOf(option.get().getIdMenu()));
        return DataRol;

    }


}
