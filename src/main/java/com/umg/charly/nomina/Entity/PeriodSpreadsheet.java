package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "periodo_planilla")
@Getter
@Setter
public class PeriodSpreadsheet {
    @EmbeddedId
    private PeriodSpreadsheetPK IdPK;

    @Column(name = "fechainicio")
    private Date startDate;

    @Column(name = "fechafin")
    private Date endDate;

    @Column(name = "fechacreacion")
    private Date creationDate;

    @Column(name = "usuariocreacion")
    private String userCreation;

    @Column(name = "fechamodificacion")
    private Date modificationDate;

    @Column(name = "usuariomodificacion")
    private String userModification;
}
