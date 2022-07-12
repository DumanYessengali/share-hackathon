package kz.nis.share.dtos.user.profile;


import kz.nis.share.entities.EDegree;
import kz.nis.share.entities.UserEducation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEducationDto {
    private String universityName;

    private String major;

    private EDegree degree;

    public UserEducationDto (UserEducation userEducation) {
        universityName = userEducation.getUniversityName();
        major = userEducation.getMajor();
        degree = userEducation.getDegree();
    }
}
