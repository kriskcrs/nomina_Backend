package com.umg.charly.nomina.Entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class PayrollHeaderPK implements Serializable {

    @Column(name = "anio")
    private Long year;
    @Column(name = "mes")
    private Long month;
}
