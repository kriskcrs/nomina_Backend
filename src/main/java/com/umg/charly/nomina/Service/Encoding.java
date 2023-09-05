package com.umg.charly.nomina.Service;

public class Encoding {
    String miString = "";
    StringBuilder nuevaP = new StringBuilder();

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



}
