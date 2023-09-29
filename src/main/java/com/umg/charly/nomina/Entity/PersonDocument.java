package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "documento_persona")
@Getter
@Setter
public class PersonDocument {
    @EmbeddedId
    private PersonDocumentPK IdPK;
    @Column(name = "nodocumento")
    private String numberDocument;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
