package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.UserQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuestionsRepository extends JpaRepository<UserQuestions,Long> {
}
