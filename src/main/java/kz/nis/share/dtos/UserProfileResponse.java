package kz.nis.share.dtos;

import kz.nis.share.entities.UserDetail;
import kz.nis.share.entities.UserEducation;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfileResponse {
    private LocalDate birthday;

    private String job;

    private String subject;

    private List<UserEducationDto> userEducationList = new ArrayList<>();

    public UserProfileResponse(UserDetail userDetail, List<UserEducation> userEducationList) {
        birthday = userDetail.getBirthday();
        job = userDetail.getJob();
        subject = userDetail.getSubject();
        for (UserEducation userEducation : userEducationList) {
            this.userEducationList.add(new UserEducationDto(userEducation));
        }

    }

}
