package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Branch {
    @Id
    @Column(name = "id_sucursal")
    private String idSucursal;
}
