package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "opcion")
@Getter
@Setter
public class Option {
    @Id
    @Column(name = "idopcion")
    private Long idOption;
    @Column(name = "nombre")
    private String name;
    @Column(name = "idmenu")
    private Long idMenu;
    @Column(name = "ordenmenu")
    private Long idOrderMenu;
    @Column(name = "pagina")
    private String page;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;


}
