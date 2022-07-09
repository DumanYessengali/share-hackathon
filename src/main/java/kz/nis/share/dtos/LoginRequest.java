package kz.nis.share.dtos;

import lombok.*;

@Data
public class LoginRequest {
    private String login;
    private String password;
}