package com.umg.charly.nomina.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "usuario_role")
@Getter
@Setter
public class UserRole {
    @Id
    @Column(name = "idusuario")
    private String idUser;
    @Column(name = "idrole")
    private Long idRole;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
