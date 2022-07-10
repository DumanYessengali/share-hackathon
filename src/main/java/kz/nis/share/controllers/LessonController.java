package kz.nis.share.controllers;

import kz.nis.share.dtos.course.LessonCreateDto;
import kz.nis.share.dtos.course.WriteCommentLessonDto;
import kz.nis.share.responses.ResponseMessage;
import kz.nis.share.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/write/{lesson_id}")
    public ResponseEntity<ResponseMessage> createCourse(@RequestBody WriteCommentLessonDto writeCommentLessonDto, @PathVariable(name = "lesson_id") Long id, Principal principal) {
        lessonService.writeComment(writeCommentLessonDto, principal.getName(), id);
        return new ResponseEntity<>(ResponseMessage.builder().statusCode(200).message("Successfully added").build(), HttpStatus.OK);
    }
}
