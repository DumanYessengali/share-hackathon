package kz.nis.share.services;

import kz.nis.share.dtos.course.WriteCommentLessonDto;
import kz.nis.share.entities.CommentLesson;
import kz.nis.share.entities.Lesson;
import kz.nis.share.entities.User;
import kz.nis.share.exceptions.CourseException;
import kz.nis.share.repositories.CommentLessonRepository;
import kz.nis.share.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserService userService;
    private final CommentLessonRepository commentLessonRepository;

    public void writeComment(WriteCommentLessonDto writeCommentLessonDto, String login, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new CourseException("Lesson not found"));
        User user = userService.findUserByLogin(login);
        CommentLesson commentLesson = new CommentLesson();
        commentLesson.setLesson(lesson);
        commentLesson.setUser(user);
        commentLesson.setContent(writeCommentLessonDto.getContent());
        commentLesson.setCreatedAt(LocalDate.now());
        commentLessonRepository.save(commentLesson);

    }
}
