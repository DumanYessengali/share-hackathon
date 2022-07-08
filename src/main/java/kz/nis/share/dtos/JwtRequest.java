package kz.nis.share.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class JwtRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
}