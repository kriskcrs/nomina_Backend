package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public Employee findByIdEmployee(long id);
}
