package kz.nis.share.dtos.course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String content;
    private String videoLink;
    private String createdDate;
    private String lessonNumber;

    private List<CommentLessonDto> commentLessonDtoList;

    public LessonDto(String title, String content, String videoLink, String createdDate) {
        this.title = title;
        this.content = content;
        this.videoLink = videoLink;
        this.createdDate = createdDate;
    }
}
