package com.umg.charly.nomina.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {

    @Id
    @Column(name = "idmenu")
    private Long idMenu;
    @Column(name = "idmodulo")
    private Long idModulo;
    @Column(name = "nombre")
    private String name;
    @Column(name = "ordenmenu")
    private Integer orderMenu;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
