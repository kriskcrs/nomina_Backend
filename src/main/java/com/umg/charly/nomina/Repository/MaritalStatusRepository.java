package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaritalStatusRepository extends JpaRepository<MaritalStatus,Long> {

    public MaritalStatus findByIdMaritalStatus (long id );
}
