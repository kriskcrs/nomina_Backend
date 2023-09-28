package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "tipo_documento")
@Getter
@Setter
public class TypeDocument {
    @Id

    @Column(name = "idtipodocumento")
    private Long idTypeDocument;

    @Column(name = "nombre")
    private String name;

    @Column(name = "fechacreacion")
    private Date creationDate;

    @Column(name = "usuariocreacion")
    private String userCreation;

    @Column(name = "fechamodificacion")
    private Date modificationDate;

    @Column(name = "usuariomodificacion")
    private String userModification;

}
