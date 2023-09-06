package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.UserQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuestionsRepository extends JpaRepository<UserQuestions,Long> {
    public List<UserQuestions> findByIdUser(String user);
}
