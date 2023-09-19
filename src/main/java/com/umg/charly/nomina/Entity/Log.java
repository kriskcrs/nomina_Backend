package com.umg.charly.nomina.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "bitacora_acceso")
@Getter
@Setter


public class Log {
    @Id
    @Column(name = "idbitacoraacceso")
    private int idLog;
    @Column(name = "idusuario")
    private String idUser;
    @Column(name = "idtipoacceso")
    private int idtypeAccess;
    @Column(name = "fechaacceso")
    private Date dateAccess;
    @Column(name = "httpuseragent")
    private String httpUserAgent;
    @Column(name = "direccionip")
    private String ipAdress;
    @Column(name = "accion")
    private String action;
    @Column(name = "sistemaoperativo")
    private String os;
    @Column(name = "dispositivo")
    private String divice;
    @Column(name = "browser")
    private String browser;
    @Column(name = "sesion")
    private String sesion;

}
