package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "flujo_status_empleado")
@Getter
@Setter
public class FlowStatusEmployee {

    @Id
    @Column(name = "idstatusactual")
    private Long idStatusCurrent;
    //pendiente crear una llave compuesta
}
