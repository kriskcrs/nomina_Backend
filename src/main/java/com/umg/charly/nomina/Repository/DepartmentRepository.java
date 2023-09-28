package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findByidDepartment(long idDepartment);
}
