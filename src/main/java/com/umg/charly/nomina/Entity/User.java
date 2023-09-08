package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "usuario")
@Getter
@Setter

public class User {

    @Id

    @Column(name = "idusuario")
    private String idUser;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "fechanacimiento")
    private Date dob;
    @Column(name = "idstatususuario")
    private Long idStatusUser;
    @Column(name = "password")
    private String password;
    @Column(name = "idgenero")
    private Long idGender;
    @Column(name = "ultimafechaingreso")
    private Date lastDateOfEntry;
    @Column(name = "intentosdeacceso")
    private Integer accessAttempts;
    @Column(name = "sesionactual")
    private String currentSession;
    @Column(name = "ultimafechacambiopassword")
    private Date lastPasswordChangeDate;
    @Column(name = "correoelectronico")
    private String email;
    @Column(name = "requierecambiarpassword")
    private Integer requiresChangingPassword;
    @Lob
    @Column(name = "fotografia")
    private byte[] photo;
    @Column(name = "telefonomovil")
    private String mobilePhone;
    @Column(name = "idsucursal")
    private Long idBranch;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;
    
}
