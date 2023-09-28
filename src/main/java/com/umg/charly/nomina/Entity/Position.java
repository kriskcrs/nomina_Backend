package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "puesto")
@Getter
@Setter

public class Position {
    @Id
    @Column(name = "idpuesto")
    private Long idPosition;
    @Column(name = "nombre")
    private String name;
    @Column(name = "iddepartamento")
    private String idDepartment;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
}
