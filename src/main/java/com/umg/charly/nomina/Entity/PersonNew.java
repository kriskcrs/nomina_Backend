package com.umg.charly.nomina.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonNew {

    private Person person;
    private Employee employee;
    private PersonDocumentTemp personDocumentTemp;
    private List<PersonDocumentTemp> personDocumentTempsList;

}
