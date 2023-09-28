package com.umg.charly.nomina.Repository;


import com.umg.charly.nomina.Entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence,Long> {
    public Absence findByIdAbsence(long id);
}
