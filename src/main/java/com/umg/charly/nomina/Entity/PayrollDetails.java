package com.umg.charly.nomina.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "planilla_detalle")
@Getter
@Setter
public class PayrollDetails {

    @Id
    @Column(name = "idplanilladetalle")
    private Long idPayrollDetails;

    @Column(name = "anio")
    private Long year;
    @Column(name = "mes")
    private Long month;
    @Column(name = "idempleado")
    private Long idEmployee;
    @Column(name = "fechacontratacion")
    private Date dateOfHire;
    @Column(name = "idpuesto")
    private Long idPosition;
    @Column(name = "idstatusempleado")
    private Long idStatusEmployee;
    @Column(name = "ingresosueldobase")
    private Double baseSalaryIncome;
    @Column(name = "ingresobonificaciondecreto")
    private Double bonusIncomeDecree;
    @Column(name = "ingresootrosingresos")
    private Double incomeOther;
    @Column(name = "descuentoigss")
    private Double igss;
    @Column(name = "descuentoisr")
    private Double isr;
    @Column(name = "descuentoinasistencias")
    private Double noShowDiscount;
    @Column(name = "salarioneto")
    private Double netSalary;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;


}
