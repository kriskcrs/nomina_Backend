package com.umg.charly.nomina.Entity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PayrollPeriodPK implements Serializable {
    @Column(name = "anio")
    private Long anio;
    @Column(name = "mes")
    private Long mes;
}
