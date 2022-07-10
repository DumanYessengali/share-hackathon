package kz.nis.share.services;

import kz.nis.share.dtos.UserDto;
import kz.nis.share.dtos.course.*;
import kz.nis.share.entities.CommentLesson;
import kz.nis.share.entities.Course;
import kz.nis.share.entities.Lesson;
import kz.nis.share.entities.User;
import kz.nis.share.exceptions.CourseException;
import kz.nis.share.exceptions.UserException;
import kz.nis.share.repositories.CourseRepository;
import kz.nis.share.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;

    private final LessonRepository lessonRepository;

    public List<AllCourses> findAll() {
        List<Course> all = courseRepository.findAll();
        List<AllCourses> collect = all.stream().map((c) -> new AllCourses(c.getId(), c.getTitle())).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public CourseInfo findById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseException("Course not found"));
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setTitle(course.getTitle());
        courseInfo.setDescription(course.getDescription());
        courseInfo.setCreatedDate(course.getCreatedDate().toString());
        courseInfo.setCreatorName(course.getUser().getName());
        courseInfo.setCreatorSurname(course.getUser().getSurname());
        courseInfo.setId(course.getId());
        List<Lesson> lessonList = course.getLessonList();
        List<LessonDto> lessonDtoList = new ArrayList<>();
        for (Lesson l : lessonList) {
            List<CommentLesson> commentLessonList = l.getCommentLessonList();
            List<CommentLessonDto> commentLessonDtoList = new ArrayList<>();
            LessonDto lessonDto = new LessonDto();
            for (CommentLesson c : commentLessonList) {
                CommentLessonDto commentLessonDto = new CommentLessonDto();
                commentLessonDto.setLessonId(l.getId());
                commentLessonDto.setCreatedAt(c.getCreatedAt().toString());
                commentLessonDto.setContent(c.getContent());
                User user = c.getUser();
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setName(user.getName());
                userDto.setSurname(user.getSurname());
                commentLessonDto.setUser(userDto);
                commentLessonDtoList.add(commentLessonDto);
            }
            lessonDto.setId(l.getId());
            lessonDto.setCommentLessonDtoList(commentLessonDtoList);
            lessonDto.setTitle(l.getTitle());
            lessonDto.setContent(l.getContent());
            lessonDto.setCreatedDate(l.getCreatedDate().toString());
            lessonDto.setVideoLink(l.getVideoLink());
            lessonDto.setLessonNumber(l.getLessonNumber());
            lessonDtoList.add(lessonDto);


        }

        courseInfo.setLessonDtoList(lessonDtoList);
        return courseInfo;
    }

    public void createCourse(CourseCreateDto courseCreateDto, String login) {
        User user = userService.findUserByLogin(login);
        if(user == null) {
            throw new UserException("User not found");
        }
        Course course = new Course();
        course.setTitle(courseCreateDto.getTitle());
        course.setDescription(courseCreateDto.getDescription());
        course.setCreatedDate(LocalDate.now());
        course.setUser(user);
        courseRepository.save(course);

    }

    public void createLesson(LessonCreateDto lessonCreateDto, Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseException("Course not found"));
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonCreateDto.getTitle());
        lesson.setContent(lessonCreateDto.getContent());
        lesson.setVideoLink(lessonCreateDto.getVideoLink());
        lesson.setCreatedDate(LocalDate.now());
        lesson.setLessonNumber(lessonCreateDto.getLessonNumber());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
    }


}
