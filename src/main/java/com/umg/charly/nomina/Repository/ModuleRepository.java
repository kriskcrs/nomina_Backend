package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module,Long> {
    public Module findByIdModule(Long idModule);
}
