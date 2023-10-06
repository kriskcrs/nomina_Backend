package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FlowStatusEmployeePK implements Serializable {
    @Column(name = "idstatusactual")
    private Long idStatusCurrent;

    @Column(name = "idstatusnuevo")
    private Long idStatusNew;
}
