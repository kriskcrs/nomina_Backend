package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Option;
import com.umg.charly.nomina.Entity.Role;
import com.umg.charly.nomina.Entity.RoleOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long>  {
}
