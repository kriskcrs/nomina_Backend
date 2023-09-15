package com.umg.charly.nomina.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "bitacora_acceso")
@Getter
@Setter


public class Log {
    @Id
    @Column(name = "IdBitacoraAcceso")
    private String idLog;
    @Column(name = "IdUsuario")
    private String idUser;
    @Column(name = "IdTipoAcceso")
    private String idtypeAccess;
    @Column(name = "FechaAcceso")
    private Date dateAccess;
    @Column(name = "HttpUserAgent")
    private String httpUserAgent;
    @Column(name = "DireccionIp")
    private String ipAdress;
    @Column(name = "Accion")
    private String action;
    @Column(name = "SistemaOperativo")
    private String os;
    @Column(name = "Dispositivo")
    private String divice;
    @Column(name = "Browser")
    private String browser;
    @Column(name = "Sesion")
    private String sesion;

}
