package com.umg.charly.nomina.Tools;


public class KeepAlive {

    public Boolean validateSession(String  session){
        if(session!=null && !session.equals("") ){
            System.out.println(session);
            return true;
        }else{
            return false;
        }
    }
}
