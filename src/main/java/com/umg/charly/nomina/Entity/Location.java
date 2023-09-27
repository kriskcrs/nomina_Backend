package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "sucursal")
@Getter
@Setter
public class Location {
    @Id
    @Column(name = "idsucursal")
    private Long idLocation;
    @Column(name = "nombre")
    private String name;
    @Column(name = "direccion")
    private String address;
    @Column(name="idempresa")
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
