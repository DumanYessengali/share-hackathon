package kz.nis.share.services;

import kz.nis.share.dtos.CommentRequest;
import kz.nis.share.entities.Post;
import kz.nis.share.entities.PostComments;
import kz.nis.share.repositories.PostCommentsRepository;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.responses.BodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCommentsService {
    private final PostCommentsRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocalDate timestamp = LocalDate.from(LocalDateTime.now());

    public List<PostComments> getPostCommentaries(Long postId) {
        return postCommentRepository.findAllByPost(postRepository.findById(postId).get());
    }

    public void createComment(String username, CommentRequest commentRequest) {
        postCommentRepository.save(new PostComments(
                commentRequest.getPostContent(), userRepository.findUserByLogin(username).get(), postRepository.findById(commentRequest.getPostId()).get(), timestamp));
    }

    public BodyResponse deletePostComment(String username, Long postId, Long commentId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("Post does not exists", Response.Status.BAD_REQUEST, null);
        }
        Optional<PostComments> comment = postCommentRepository.findById(commentId);
        if (comment.isEmpty()) {
            return new BodyResponse("comment does not exists", Response.Status.BAD_REQUEST, null);
        }
        if (comment.get().getUser().getLogin().equals(username)) {
            postCommentRepository.delete(comment.get());
            return new BodyResponse("Post successfully deleted", Response.Status.OK, null);
        }
        return new BodyResponse("Not enough permission", Response.Status.BAD_REQUEST, null);


    }
}
