package kz.nis.share.dtos;

import java.time.LocalDate;
import java.util.List;

public class UserDetailRequest {
    private LocalDate birthday;

    private String job;

    private String subject;

    private List<UserEducationDto> userEducationList;
}
