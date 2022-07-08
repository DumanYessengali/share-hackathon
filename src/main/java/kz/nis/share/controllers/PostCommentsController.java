package kz.nis.share.controllers;

import kz.nis.share.services.PostCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/comment")
public class PostCommentsController {
    private final PostCommentsService postCommentsService;
}
