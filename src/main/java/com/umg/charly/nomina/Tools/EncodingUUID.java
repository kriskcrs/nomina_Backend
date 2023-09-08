package com.umg.charly.nomina.Tools;

import java.util.UUID;

public class EncodingUUID {
    private UUID sessionId;

    public UUID SessionManager(){
        return sessionId = UUID.randomUUID();
    }

}
