package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.RoleOption;
import com.umg.charly.nomina.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleOptionRepository extends JpaRepository<RoleOption,Long> {

    public RoleOption findByIdRole(Long idRole);
}
