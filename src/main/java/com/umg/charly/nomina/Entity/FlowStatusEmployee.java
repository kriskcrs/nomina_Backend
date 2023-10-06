package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "flujo_status_empleado")
@Getter
@Setter
public class FlowStatusEmployee {
    @EmbeddedId
    private FlowStatusEmployeePK idPK;

    @Column(name = "nombreevento")
    private String nameEvent;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;
}
