package com.umg.charly.nomina.Tools;

import java.util.UUID;

public class Encoding {
    String miString = "";
    StringBuilder nuevaP = new StringBuilder();
    UUID sessionId;

    public String crypt(String texto) {
       
        int valor = texto.length();
        //encripta
        for (char caracter : texto.toCharArray()) {
            int vAsciiM = (int) caracter + valor;
            char caracterM = (char) vAsciiM;
            nuevaP.append(vAsciiM);
        }
        return nuevaP.toString();
    }


    public String crypt2(String texto) {
        StringBuilder nuevaP = new StringBuilder();
        int valor = texto.length();
        
        for (char caracter : texto.toCharArray()) {
            int vAsciiM = (int) caracter + valor;
            nuevaP.append((char) vAsciiM); // Usa los caracteres modificados
        }
        
        return nuevaP.toString();
    }
    

    public UUID SessionManager(){
        return sessionId = UUID.randomUUID();
    }

}
