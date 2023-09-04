package com.umg.charly.nomina.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.Date;


@Entity
@Table(name = "usuario")
public class User {
    @Id
    @Column(name = "idusuario")
    private String idUser;

    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "fechanacimiento")
    private Date dob;
    @Column(name = "idstatususuario")
    private Long idStatusUser;
    @Column(name = "password")
    private String password;
    @Column(name = "idgenero")
    private Long idGender;
    @Column(name = "ultimafechaingreso")
    private Date lastDateOfEntry;
    @Column(name = "intentosdeacceso")
    private Integer accessAttempts;
    @Column(name = "sesionactual")
    private String currentSession;
    @Column(name = "ultimafechacambiopassword")
    private Date lastPasswordChangeDate;
    @Column(name = "correoelectronico")
    private String email;
    @Column(name = "requierecambiarpassword")
    private Integer requiresChangingPassword;
    @Lob
    @Column(name = "fotografia")
    private byte[] photo;
    @Column(name = "telefonomovil")
    private String mobilePhone;
    @Column(name = "idsucursal")
    private Long idBranch;
    @Column(name = "fechacreacion")
    private Date creationDate;
    @Column(name = "usuariocreacion")
    private String userCreation;
    @Column(name = "fechamodificacion")
    private Date modificationDate;
    @Column(name = "usuariomodificacion")
    private String userModification;


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Long getIdStatusUser() {
        return idStatusUser;
    }

    public void setIdStatusUser(Long idStatusUser) {
        this.idStatusUser = idStatusUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdGender() {
        return idGender;
    }

    public void setIdGender(Long idGender) {
        this.idGender = idGender;
    }

    public Date getLastDateOfEntry() {
        return lastDateOfEntry;
    }

    public void setLastDateOfEntry(Date lastDateOfEntry) {
        this.lastDateOfEntry = lastDateOfEntry;
    }

    public Integer getAccessAttempts() {
        return accessAttempts;
    }

    public void setAccessAttempts(Integer accessAttempts) {
        this.accessAttempts = accessAttempts;
    }

    public String getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(String currentSession) {
        this.currentSession = currentSession;
    }

    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRequiresChangingPassword() {
        return requiresChangingPassword;
    }

    public void setRequiresChangingPassword(Integer requiresChangingPassword) {
        this.requiresChangingPassword = requiresChangingPassword;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Long idBranch) {
        this.idBranch = idBranch;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getUserModification() {
        return userModification;
    }

    public void setUserModification(String userModification) {
        this.userModification = userModification;
    }
}
