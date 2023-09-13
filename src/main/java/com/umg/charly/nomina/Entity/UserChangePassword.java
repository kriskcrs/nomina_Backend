package com.umg.charly.nomina.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePassword {
    private String idUser;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
}
