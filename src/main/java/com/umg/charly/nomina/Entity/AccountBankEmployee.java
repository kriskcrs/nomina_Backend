package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "cuenta_bancaria_empleado")
@Getter
@Setter
public class AccountBankEmployee {

    @Id
    @Column(name = "idcuentabancaria")
    private Long idAccountBank;
    @Column(name = "idempleado")
    private Long idEmployee;
    @Column(name = "idbanco")
    private Long idBank;
    @Column (name = "numerodecuenta")
    private String accountNumber;
    @Column (name = "activa")
    private Integer active;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
