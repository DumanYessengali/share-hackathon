package kz.nis.share.dtos.user.profile;

import java.time.LocalDate;

public interface UserProfileInfo {
    LocalDate getBirthday();
    String getJob();
    String getSubject();
}
