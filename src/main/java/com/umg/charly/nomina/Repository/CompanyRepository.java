package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
