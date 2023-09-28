package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long>{
    public Person findByIdPerson(long id);
}
