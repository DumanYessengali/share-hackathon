package kz.nis.share.controllers;


import kz.nis.share.responses.BodyResponse;
import kz.nis.share.services.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/like")
public class PostLikesController {
    private final PostLikesService postLikesService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> likeThePost(@PathVariable Long postId, Principal principal) {
        BodyResponse response = postLikesService.likeThePost(postId, principal.getName());
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostLikes(@PathVariable Long postId) {
        BodyResponse response = postLikesService.getPostLikes(postId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        if (response.getBody() == null) {
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(response.getBody());

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> removeLikeFromPost(@PathVariable Long postId, Principal principal) {
        BodyResponse response = postLikesService.removeLikeFromPost(principal.getName(), postId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/all-post-liked-users/{postId}")
    public ResponseEntity<?> postLikedUsers(@PathVariable Long postId) {
        BodyResponse response = postLikesService.postLikedUsers(postId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/isLiked/{postId}")
    public ResponseEntity<?> isUserLiked(@PathVariable Long postId, Principal principal) {
        BodyResponse response = postLikesService.isLiked(principal.getName(), postId);
        if (response.getStatusCode() != Response.Status.OK) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }

        return ResponseEntity.ok(response.getBody());
    }
}
