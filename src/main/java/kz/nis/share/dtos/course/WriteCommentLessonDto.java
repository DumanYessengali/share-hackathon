package kz.nis.share.dtos.course;

import kz.nis.share.entities.Lesson;
import kz.nis.share.entities.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
public class WriteCommentLessonDto {

    private String content;

}
