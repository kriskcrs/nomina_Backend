package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.StatusUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUserRepository extends JpaRepository<StatusUser,Long> {

    public  StatusUser findByIdStatusUser ( long id);
}
