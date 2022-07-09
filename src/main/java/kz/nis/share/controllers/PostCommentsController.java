package kz.nis.share.controllers;


import kz.nis.share.dtos.CommentRequest;
import kz.nis.share.responses.BodyResponse;
import kz.nis.share.services.PostCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearer")
@RequestMapping("/post/comment")
public class PostCommentsController {
    private final PostCommentsService postCommentsService;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostCommentaries(@PathVariable Long postId) {
        return ResponseEntity.ok(postCommentsService.getPostCommentaries(postId));
    }

    @PostMapping
    public ResponseEntity<?> createComment(Principal principal, @RequestBody CommentRequest commentRequest) {
        postCommentsService.createComment(principal.getName(), commentRequest);
        return ResponseEntity.ok("comment created");
    }

    @DeleteMapping
    public ResponseEntity<?> deletePostComment(Principal principal, @RequestParam("post_id") Long postId,@RequestParam("comment_id") Long commentId) {
        BodyResponse response = postCommentsService.deletePostComment(principal.getName(), postId, commentId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.ok(response.getMessage());
    }
}
