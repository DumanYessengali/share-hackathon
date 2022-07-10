package kz.nis.share.dtos.course;

import kz.nis.share.dtos.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommentLessonDto {

    private String content;
    private UserDto user;
    private Long lessonId;
    private String createdAt;

    public CommentLessonDto(String content, UserDto user, Long lessonId, String createdAt) {
        this.content = content;
        this.user = user;
        this.lessonId = lessonId;
        this.createdAt = createdAt;
    }
}
