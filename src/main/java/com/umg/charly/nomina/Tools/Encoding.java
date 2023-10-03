package com.umg.charly.nomina.Tools;

import java.util.UUID;

public class Encoding {
    String miString = "";
    StringBuilder nuevaP = new StringBuilder();
    UUID sessionId;

    public String crypt(String texto) {
        miString = texto;
        int valor = texto.length();
        //encripta
        for (char caracter : miString.toCharArray()) {
            int vAsciiM = (int) caracter + valor;
            char caracterM = (char) vAsciiM;
            nuevaP.append(vAsciiM);
        }
        return nuevaP.toString();
    }


    public UUID SessionManager(){
        return sessionId = UUID.randomUUID();
    }

}
