package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
