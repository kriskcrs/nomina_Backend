package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "departamento")
@Getter
@Setter
public class Department {
    @Id

    @Column(name = "iddepartamento")
    private Long idDepartment;

    @Column(name = "nombre")
    private String name;

    @Column(name = "idempresa")
    private Long idCompany;

    @Column(name = "fechacreacion")
    private Date creationDate;

    @Column(name = "usuariocreacion")
    private String userCreation;

    @Column(name = "fechamodificacion")
    private Date modificationDate;

    @Column(name = "usuariomodificacion")
    private String userModification;
}
