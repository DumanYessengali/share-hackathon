package kz.nis.share.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    @ResponseBody
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionMessage> userException1(UserException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(UserDetailNotFound.class)
    public ResponseEntity<ExceptionMessage> userException2(UserDetailNotFound ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ArticleException.class)
    public ResponseEntity<ExceptionMessage> userException2(ArticleException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ExceptionMessage> userException2(CourseException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(PostException.class)
    public ResponseEntity<ExceptionMessage> userException3(PostException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }






}
