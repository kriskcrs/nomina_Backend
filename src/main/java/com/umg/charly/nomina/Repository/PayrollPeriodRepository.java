package com.umg.charly.nomina.Repository;
import com.umg.charly.nomina.Entity.PayrollPeriod;
import com.umg.charly.nomina.Entity.PayrollPeriodPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollPeriodRepository extends JpaRepository<PayrollPeriod, PayrollPeriodPK> {
}
