package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "usuario_pregunta")
@Getter
@Setter
public class UserQuestions {
    @Id
    @Column(name = "idpregunta")
    private Long idQuestion;
    @Column(name = "idusuario")
    private String idUser;
    @Column(name = "pregunta")
    private String questions;
    @Column(name = "respuesta")
    private String respond;
    @Column(name = "ordenpregunta")
    private Integer orderQuestions;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;

}
