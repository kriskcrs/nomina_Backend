package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "planilla_cabecera")
@Getter
@Setter
public class PayrollHeader {

    @EmbeddedId
    private PayrollHeaderPK IdPK;
    @Column(name = "totalingresos")
    private Double totalIncome;
    @Column(name = "totaldescuentos")
    private Double totalDiscounts;
    @Column(name = "salarioNeto")
    private Double salary;
    @Column(name = "fechahoraprocesada")
    private Date dateProcess;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;
}
