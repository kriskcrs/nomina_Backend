package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,String> {

    public UserRole findByIdUser(String idUser);
}
