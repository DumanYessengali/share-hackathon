package kz.nis.share.dtos.course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CourseInfo {
    private Long id;
    private String title;
    private String description;
    private String createdDate;
    private String creatorName;
    private String creatorSurname;
    private List<LessonDto> lessonDtoList;

}
