package kz.nis.share.dtos;

import java.time.LocalDate;

public interface UserProfileInfo {
    LocalDate getBirthday();
    String getJob();
    String getSubject();
}
