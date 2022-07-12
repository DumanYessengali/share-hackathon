package kz.nis.share.dtos.user.profile;

import lombok.Data;

@Data
public class UserBasicInfoResponse {
    public String name;
    public String surname;
    public String email;
    public Long userId;

    public UserBasicInfoResponse(String name, String surname, String email, Long userId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userId = userId;
    }
}
