package kz.nis.share.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kz.nis.share.services.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer")
@RequestMapping("/post/like")
public class PostLikesController {
    private final PostLikesService postLikesService;
}
