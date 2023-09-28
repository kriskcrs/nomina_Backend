package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Entity
@Table(name = "inasistencia")
@Getter
@Setter

public class Absence {
    @Id
    @Column(name = "idinasistencia")
    private Long idAbsence;
    @Column(name = "idempleado")
    private String idEmployee;
    @Column(name = "fechainicial")
    private Date initialDate;
    @Column(name = "fechafinal")
    private Date finalDate;
    @Column(name = "motivoinasistencia")
    private String reason;
    @Column(name = "fechaprocesado")
    private Date processingDate;
    @Column(name = "fechacreacion")
    private Date createDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
