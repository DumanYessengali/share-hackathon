package kz.nis.share.dtos.course;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class LessonCreateDto {

    private String title;
    private String content;
    private String videoLink;
    private String lessonNumber;


}
