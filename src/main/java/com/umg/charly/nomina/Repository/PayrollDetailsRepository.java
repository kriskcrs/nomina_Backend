package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.PayrollDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollDetailsRepository extends JpaRepository<PayrollDetails,Long> {
}
