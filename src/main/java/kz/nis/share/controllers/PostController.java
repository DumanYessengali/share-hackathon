package kz.nis.share.controllers;


import kz.nis.share.dtos.PostDto;
import kz.nis.share.dtos.PostRequest;
import kz.nis.share.entities.Post;
import kz.nis.share.responses.BodyResponse;
import kz.nis.share.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearer")
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(Principal principal, @RequestBody PostRequest postRequest) {
        postService.save(principal.getName(), postRequest);
        return ResponseEntity.ok("Post successfully created");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/my-posts")
    public ResponseEntity<?> getAllMyPosts(Principal principal) {
        return ResponseEntity.ok(postService.getAllMyPosts(principal.getName()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById( @PathVariable Long postId) {
        BodyResponse response = postService.getPostById(postId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(Principal principal, @PathVariable Long postId) {
        BodyResponse response = postService.deletePost(principal.getName(), postId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.ok(response.getMessage());
    }

}
