package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long>  {

public Option findByIdOption( long id);
}
