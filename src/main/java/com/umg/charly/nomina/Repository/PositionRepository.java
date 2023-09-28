package com.umg.charly.nomina.Repository;


import com.umg.charly.nomina.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Long> {

    public Position findByIdPosition(long id);

}
