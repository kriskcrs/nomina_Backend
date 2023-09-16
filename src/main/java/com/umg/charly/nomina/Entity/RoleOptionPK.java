package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleOptionPK implements Serializable {
    @Column(name = "idrole")
    private Long idRole;
    @Column(name = "idopcion")
    private Long idOption;

}
