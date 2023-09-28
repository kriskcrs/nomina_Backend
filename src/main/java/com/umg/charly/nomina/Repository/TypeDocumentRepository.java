package com.umg.charly.nomina.Repository;


import com.umg.charly.nomina.Entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long>  {

    public TypeDocument findByidTypeDocument(long idTypeDocument);
}
