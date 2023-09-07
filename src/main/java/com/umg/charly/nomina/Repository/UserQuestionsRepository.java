package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.UserQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQuestionsRepository extends JpaRepository<UserQuestions,Long> {
    public List<UserQuestions> findByIdUser(String user);

    public Optional<UserQuestions> findByIdUserAndAndQuestionsAndAndRespond( String idUser, String Question, String Response);
}
