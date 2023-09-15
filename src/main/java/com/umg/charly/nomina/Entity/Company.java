package com.umg.charly.nomina.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "empresa")
@Getter
@Setter
public class Company {

    @Id
    @Column(name = "idempresa")
    private Long idCompany;
    @Column(name = "nombre")
    private String name;
    @Column(name = "direccion")
    private String address;
    @Column(name = "nit")
    private String nit;
    @Column(name = "passwordcantidadmayusculas")
    private Integer passwordAmountUppercase;
    @Column(name = "passwordcantidadminusculas")
    private Integer passwordAmountLowercase;
    @Column(name = "passwordcantidadcaracteresespeciales")
    private Integer passwordAmountSpecialCharacters;
    @Column(name = "passwordcantidadcaducidaddias")
    private Integer passwordAmountExpirationsDays;
    @Column(name = "passwordlargo")
    private Integer passwordlength;
    @Column(name = "passwordintentosantesdebloquear")
    private Integer passwordAmountAttemptsBeforeBlocking;
    @Column(name = "passwordcantidadnumeros")
    private Integer passwordAmountNumber;
    @Column(name = "passwordcantidadpreguntasvalidar")
    private Integer passwordAmountQuestionsValidate;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
