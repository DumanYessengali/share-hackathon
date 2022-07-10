package kz.nis.share.controllers;

import kz.nis.share.dtos.ArticleResponse;
import kz.nis.share.dtos.course.AllCourses;
import kz.nis.share.dtos.course.CourseCreateDto;
import kz.nis.share.dtos.course.CourseInfo;
import kz.nis.share.dtos.course.LessonCreateDto;
import kz.nis.share.responses.ResponseMessage;
import kz.nis.share.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<AllCourses> getAllCourses() {

        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public CourseInfo getCourseById(@PathVariable Long id) {

        return courseService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createCourse(@RequestBody CourseCreateDto courseCreateDto, Principal principal) {
        courseService.createCourse(courseCreateDto, principal.getName());
        return new ResponseEntity<>(ResponseMessage.builder().statusCode(200).message("Successfully added").build(), HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<ResponseMessage> createCourse(@RequestBody LessonCreateDto lessonCreateDto, @PathVariable Long id) {
        courseService.createLesson(lessonCreateDto, id);
        return new ResponseEntity<>(ResponseMessage.builder().statusCode(200).message("Successfully added").build(), HttpStatus.OK);
    }




}
