package com.umg.charly.nomina.Repository;

import com.umg.charly.nomina.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {

    public Location findByIdLocation(long idLocation);
}
