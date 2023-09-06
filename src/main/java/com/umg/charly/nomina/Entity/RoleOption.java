package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "role_opcion")
@Getter
@Setter
public class RoleOption {
    @Id
    @Column(name = "idrole")
    private Long idRole;
    @Column(name = "idopcion")
    private Long idOption;
    @Column(name = "alta")
    private Integer up;
    @Column(name = "baja")
    private Integer down;
    @Column(name = "cambio")
    private Integer update;
    @Column(name = "imprimir")
    private Integer print;
    @Column(name = "Exportar")
    private Integer export;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
