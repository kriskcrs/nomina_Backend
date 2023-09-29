package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.AccountBankEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBankEmployeeRepository extends JpaRepository<AccountBankEmployee, Long> {
}
