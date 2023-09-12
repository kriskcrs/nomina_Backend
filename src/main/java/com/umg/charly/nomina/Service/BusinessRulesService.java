package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Entity.Module;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.Encoding;
import com.umg.charly.nomina.Tools.KeepAlive;
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

    //vars
    String ok = "Se actualiza";
    String error = "La contraseña minima debe ser mayor a 5 caracteres";
    String fails = "No se puede actualizar";
    String sessionOk ="sesion no activa";
    String sessionFail ="sesion no activa";

    HashMap<String, String> response = new HashMap<>();



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
    @PostMapping(path = "/questionUser/validation")
    private HashMap<String, String> validateQuestion(@RequestBody List<UserQuestions> userQuestions) {
        List<Company> company = companyRepository.findAll();
        int count = company.get(0).getPasswordAmountQuestionsValidate();
        HashMap<String, String> message = new HashMap<>();
        User user = userRepository.findByIdUser(userQuestions.get(0).getIdUser());
        if(new KeepAlive().validateSession(user.getCurrentSession())){
            System.out.println("esta la sesion activa");
            if (userQuestions != null && !userQuestions.isEmpty()) {
                int OK = 0;
                int tmp = 0;
                for (UserQuestions userQuestionData: userQuestions
                ) {
                    System.out.println(userQuestions.get(tmp).getRespond());
                    userQuestionData.setRespond(new Encoding().crypt(userQuestionData.getRespond()));
                    Optional<UserQuestions> userQuestion = userQuestionsRepository.findByIdUserAndAndQuestionsAndAndRespond(userQuestionData.getIdUser(),userQuestionData.getQuestions(),userQuestionData.getRespond());
                    tmp++;
                    if(userQuestion.isPresent()){
                        OK++;
                    }
                }
                if(OK == count){
                    message.put("code","0");
                    message.put("message",sessionOk);
                    return message;
                }
            }
        }else{
            System.out.println("sesion no activa");
            message.put("code","1");
            message.put("message",sessionFail);
            return message;
        }
        message.put("code","1");
        message.put("message",fails);
        return message;

    }
    @PostMapping(path = "/questionsCreate")
    private UserQuestions createQuestions(@RequestBody UserQuestions userQuestions){
        User user = userRepository.findByIdUser(userQuestions.getIdUser());
        if(new KeepAlive().validateSession(user.getCurrentSession())){
            if(userQuestions != null){
                long id = userQuestionsRepository.findAll().size();
                id++;
                List<UserQuestions> questions = userQuestionsRepository.findByIdUser(userQuestions.getIdUser());
                int temp = questions.size();
                temp++;
                userQuestions.setRespond(new Encoding().crypt(userQuestions.getRespond()));
                userQuestions.setOrderQuestions(temp);
                userQuestions.setIdQuestion(id);
                return userQuestionsRepository.save(userQuestions);
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
    private HashMap<String, String> modify(@RequestBody Company company){
        if(company.getIdcompany() != null){
            if(company.getPasswordlength()>5){
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
    @GetMapping(path = "/encripta/{text}")
    private String encrip(@PathVariable String text){
        return new Encoding().crypt(text);
    }

}
