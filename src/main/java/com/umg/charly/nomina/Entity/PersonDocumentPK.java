package com.umg.charly.nomina.Entity;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PersonDocumentPK implements Serializable {
    @Column(name = "idtipodocumento")
    private Long idTypeDocument;
    @Column(name = "idpersona")
    private Long idPerson;

}
