package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "persona")
@Getter
@Setter
public class Person {
    @Id
    @Column(name = "idpersona")
    private Long idPerson;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastname;
    @Column(name = "fechanacimiento")
    private Date dob;
    @Column(name = "idgenero")
    private Long idGender;
    @Column(name = "direccion")
    private String Address;
    @Column(name = "telefono")
    private String phone;
    @Column(name = "correoelectronico")
    private String email;
    @Column(name = "idestadocivil")
    private String IdCivilstatus;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
