package kz.nis.share.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String name;
    private String surname;
    private String email;
    private String password;

    public String getLogin() {
        return name + "." + surname;
    }
}