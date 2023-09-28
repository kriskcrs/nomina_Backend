package com.umg.charly.nomina.Service;


import com.umg.charly.nomina.Entity.*;

import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.Encoding;
import com.umg.charly.nomina.Tools.KeepAlive;
import com.umg.charly.nomina.Tools.PasswordGenerator;
import com.umg.charly.nomina.Tools.SendPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("v1")
@CrossOrigin
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserQuestionsRepository userQuestionsRepository;
    @Autowired
    CompanyRepository companyRepository;

    //vars
    String fails = "Su contraseña no cumple con las condiciones ";
    String errorClient = "Cliente no existe";
    String errorParameters = "Error en parametros";
    String OK = "Exitoso";
    String sessionOk = "sesion no activa";
    String responseFail = "Respuestas no validas";
    String delete = "El registro fue eliminado exitosamente";
    String delelteE = "El registro tiene mas dependencias no puede ser borrado";
    HashMap<String, String> response = new HashMap<>();
    //parametros para generacion de contraseña por email
    int MaxCharterPassword = 25;
    int uppercaseCount = 2;
    int lengtPasswordTemp = 8;
    int lowercaseCount = 3;
    int digitCount = 3;


    //services User
    @GetMapping(path = "/user")
    private List<User> userList() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/user/{id}")
    private User userFind(@PathVariable String id) {
        return userRepository.findByIdUser(id);
    }

    @GetMapping(path = "/userRole/{idUser}")
    private UserRole userRoleListId(@PathVariable String idUser) {
        return userRoleRepository.findByIdUser(idUser);
    }

    @PostMapping(path = "/resetPassword")
    private HashMap<String, String> reset(@RequestBody User user) {

        if (user.getIdUser() != null && (user.getCurrentSession() == null || !user.getCurrentSession().equals(""))) {
            User dataUser = userRepository.findByIdUser(user.getIdUser());
            if (dataUser != null) {
                if (validateRules(user.getPassword())) {
                    dataUser.setPassword(new Encoding().crypt(user.getPassword()));
                    dataUser.setCurrentSession(null);
                    dataUser.setAccessAttempts(0);
                    dataUser.setRequiresChangingPassword(0);
                    dataUser.setIdStatusUser(1L);
                    dataUser.setLastPasswordChangeDate(new Date());
                    dataUser.setLastDateOfEntry(new Date());
                    userRepository.save(dataUser);
                    response.put("code", "0");
                    response.put("message", OK);
                    return response;
                }
                response.put("code", "1");
                response.put("message", fails);
                return response;
            }
            response.put("code", "1");
            response.put("message", errorClient);
            return response;
        }
        response.put("code", "1");
        response.put("message", errorParameters);
        return response;
    }


    @PostMapping(path = "/changePassword")
    private HashMap<String, String> changePassword(@RequestBody UserChangePassword userChangePassword) {

        if (new KeepAlive().validateSession(userRepository.findByIdUser(userChangePassword.getIdUser()).getCurrentSession())) {
            User user = userRepository.findByIdUserAndPassword(userChangePassword.getIdUser(), new Encoding().crypt(userChangePassword.getPassword()));
            if (user != null) {
                if (userChangePassword.getNewPassword().equals(userChangePassword.getConfirmNewPassword())) {

                    //validación de contraseñas
                    if (validateRules(userChangePassword.getConfirmNewPassword())) {
                        user.setPassword(new Encoding().crypt(userChangePassword.getConfirmNewPassword()));
                        user.setLastDateOfEntry(new Date());
                        user.setLastPasswordChangeDate(new Date());
                        user.setRequiresChangingPassword(0);
                        user.setAccessAttempts(0);
                        user.setCurrentSession("");

                        response.put("code", "0");
                        response.put("message", "");
                        userRepository.save(user);
                        return response;
                    } else {
                        response.put("code", "1");
                        response.put("message", "Contraseña no cumple con las politicas");
                        return response;
                    }

                } else {
                    response.put("code", "1");
                    response.put("message", "Contraseñas no coinciden");
                    return response;
                }
            } else {
                response.put("code", "1");
                response.put("message", "Contraseña  incorrecta");
                return response;
            }
        } else {
            response.put("code", "401");
            response.put("message", "No cuenta con sesión valida");
            return response;
        }
    }


    //Questions User
    @GetMapping(path = "/questionUserAll/{user}")
    private List<UserQuestions> userQuestionsAll(@PathVariable String user) {

        if (userQuestionsRepository.findByIdUser(user).isEmpty()) {
            return null;
        } else {
            return userQuestionsRepository.findByIdUser(user);
        }


    }

    @GetMapping(path = "/questionsUser/{user}")
    private List<UserQuestions> userQuest(@PathVariable String user) {
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
        int OK = 0;
        int Question = 0;
        if (!userQuestions.isEmpty()) {
            for (UserQuestions userQuestionData : userQuestions
            ) {
                userQuestionData.setRespond(new Encoding().crypt(userQuestionData.getRespond()));
                Optional<UserQuestions> userQuestion = userQuestionsRepository.findByIdUserAndAndQuestionsAndAndRespond(userQuestionData.getIdUser(), userQuestionData.getQuestions(), userQuestionData.getRespond());
                Question++;
                if (userQuestion.isPresent()) {
                    OK++;
                }
            }
            if (OK == count) {
                response.put("code", "0");
                response.put("message", "OK");
                return response;
            }
            if (Question == OK) {
                response.put("code", "1");
                response.put("message", "No posee la cantidad de preguntas necesarias, configuradas en el sistema, por favor comunicarse con el administrador");
                return response;
            } else {
                response.put("code", "1");
                response.put("message", "Respuesta No Valida");
                return response;
            }
        }
        response.put("code", "1");
        response.put("message", "No hay preguntas para validar");
        return response;
    }


    @PostMapping(path = "/questionsCreate")
    private HashMap<String, String> createQuestions(@RequestBody UserQuestions userQuestions) {
        User user = userRepository.findByIdUser(userQuestions.getIdUser());
        if (new KeepAlive().validateSession(user.getCurrentSession())) {
            if (QuestionsValidate(userQuestions)) {
                createQuestion(userQuestions);
                response.put("code", "0");
                response.put("message", OK);
                System.out.println(response);
                return response;
            }
            response.put("code", "1");
            response.put("message", "Pregunta ya existe");
            System.out.println(response);
            return response;
        }
        response.put("code", "1");
        response.put("message", "Sesion no existe");
        System.out.println(response);
        return response;

    }


    @PostMapping(path = "/createUser")
    public HashMap<String, String> createUser(@RequestBody User user) {
        try {
            User existingUser = userRepository.findByIdUser(user.getIdUser());
            if (existingUser != null) {
                String errorMessage = "Error: El correo '" + user.getIdUser() + "' ya se encuentra registrado, prueba con otro.";
                response.put("code", "1");
                response.put("message", errorMessage);
                return response;
            }
            // Va a encriptar la contraseña :D
            String generatedPassword = new PasswordGenerator().generatePassword(lengtPasswordTemp, uppercaseCount, lowercaseCount, digitCount);
            user.setPassword(new Encoding().crypt(generatedPassword));
            SendPassword.sendPasswordByEmail(user.getIdUser(), generatedPassword);
            user.setCreationDate(new Date());
            //System.out.println(user.getCreationDate());
            userRepository.save(user);
            response.put("code", "0");
            response.put("message", OK);
            return response;

        } catch (Exception e) {
            String errorMessage = "Error al crear el usuario: " + e.getMessage();
            System.err.println(errorMessage);

            response.put("code", "1");
            response.put("message", errorMessage);
            return response;
        }
    }

    @PutMapping(path = "/modifyUser/{idUser}")
    private HashMap<String, String> modifyUser(@RequestBody User user, @PathVariable String idUser) {
        if (idUser != null) {
            Optional<User> dataUser = Optional.ofNullable(userRepository.findByIdUser(idUser));
            if (dataUser.isPresent()) {
                dataUser.get().setName(user.getName());
                dataUser.get().setLastName(user.getLastName());
                //dataUser.get().setDob(user.getDob());
                dataUser.get().setIdStatusUser(user.getIdStatusUser());
                dataUser.get().setEmail(user.getEmail());
                dataUser.get().setMobilePhone(user.getMobilePhone());
                dataUser.get().setIdBranch(user.getIdBranch());
                dataUser.get().setModificationDate(new Date());
                dataUser.get().setUserModification(user.getUserModification());
                userRepository.save(dataUser.get());
                response.put("code", "0");
                response.put("message", "Se actualizo exitosamente");
                return response;
            }
        }
        response.put("code", "1");
        response.put("message", "No se actualizo");
        return response;
    }

    @DeleteMapping(path = "/deleteUser/{id}")
    private HashMap<String, String> deleteUser(@PathVariable String id) {
        try {
            userRepository.deleteById(Long.valueOf(id));
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    //validaciones de reglas
    private Boolean validateRules(String dataPass) {
        if (dataPass != null) {
            Optional<Company> dataCompany = companyRepository.findById(1L);
            int mayusculasRequeridas = dataCompany.get().getPasswordAmountUppercase();
            int minusculasRequeridas = dataCompany.get().getPasswordAmountLowercase();
            int numerosRequeridos = dataCompany.get().getPasswordAmountNumber();
            int simbolosRequeridos = dataCompany.get().getPasswordAmountSpecialCharacters();
            int longitudMinima = dataCompany.get().getPasswordlength();
            int longitudMaxima = MaxCharterPassword;

            if (dataPass.length() < longitudMinima || dataPass.length() > longitudMaxima) {
                return false;
            }
            int mayusculasEncontradas = 0;
            int minusculasEncontradas = 0;
            int numerosEncontrados = 0;
            int simbolosEncontrados = 0;

            for (int i = 0; i < dataPass.length(); i++) {
                char caracter = dataPass.charAt(i);
                if (Character.isUpperCase(caracter)) {
                    mayusculasEncontradas++;
                } else if (Character.isLowerCase(caracter)) {
                    minusculasEncontradas++;
                } else if (Character.isDigit(caracter)) {
                    numerosEncontrados++;
                } else {
                    simbolosEncontrados++;
                }
            }

            if (mayusculasEncontradas >= mayusculasRequeridas) {
                System.out.println("mayusculas -> OK " + mayusculasEncontradas);
                if (minusculasEncontradas >= minusculasRequeridas) {
                    System.out.println("minusculas -> OK " + minusculasEncontradas);
                    if (numerosEncontrados >= numerosRequeridos) {
                        System.out.println("numeros -> OK " + numerosEncontrados);
                        if (simbolosEncontrados >= simbolosRequeridos) {
                            System.out.println("simbolo -> OK " + simbolosEncontrados);
                            return true;
                        } else {
                            System.out.println("simbolo -> Fail");
                        }
                    } else {
                        System.out.println("numeros -> Fail");
                    }
                } else {
                    System.out.println("minusculas -> Fail");
                }
            } else {
                System.out.println("mayusculas -> Fail");
            }
        }
        return false;
    }

    private void createQuestion(UserQuestions userQuestions) {
        long id = userQuestionsRepository.findAll().size();
        id++;
        List<UserQuestions> questions = userQuestionsRepository.findByIdUser(userQuestions.getIdUser());
        int temp = questions.size();
        temp++;
        userQuestions.setRespond(new Encoding().crypt(userQuestions.getRespond()));
        userQuestions.setOrderQuestions(temp);
        userQuestions.setIdQuestion(id);
        userQuestions.setCreationDate(new Date());
        userQuestionsRepository.save(userQuestions);

    }

    private boolean QuestionsValidate(UserQuestions userQuestions) {
        UserQuestions question = userQuestionsRepository.findByIdUserAndAndQuestions(userQuestions.getIdUser(), userQuestions.getQuestions());
        if (question == null) {
            return true;
        } else {
            return false;
        }
    }

}

