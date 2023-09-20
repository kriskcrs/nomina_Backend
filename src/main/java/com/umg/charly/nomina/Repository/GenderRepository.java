package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender,Long>{
}
