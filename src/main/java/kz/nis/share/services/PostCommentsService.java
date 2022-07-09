package kz.nis.share.services;

import kz.nis.share.dtos.CommentRequest;
import kz.nis.share.dtos.PostCommentsDto;
import kz.nis.share.dtos.PostDto;
import kz.nis.share.dtos.UserDto;
import kz.nis.share.entities.Post;
import kz.nis.share.entities.PostComments;
import kz.nis.share.repositories.PostCommentsRepository;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.responses.BodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCommentsService {
    private final PostCommentsRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PostCommentsDto> getPostCommentaries(Long postId) {
        List<PostComments> comments = postCommentRepository.findAllByPost(postRepository.findById(postId).get());
        System.out.println(comments);
        List<PostCommentsDto> postCommentsDtos = new ArrayList<>();
        for (PostComments postComment : comments) {
            PostCommentsDto postCommentsDto = fillPostCommentsDto(new PostCommentsDto(), postComment);
            postCommentsDtos.add(postCommentsDto);
        }

        return postCommentsDtos;
    }

    @Transactional
    public void createComment(String username, CommentRequest commentRequest) {
        PostComments postComments = new PostComments();
        postComments.setPostContent(commentRequest.getPostContent());
        postComments.setUser(userRepository.findUserByLogin(username).get());
        postComments.setPost(postRepository.findById(commentRequest.getPostId()).get());
        postComments.setCreatedAt(LocalDate.now());
        postCommentRepository.save(postComments);
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
            return new BodyResponse("Comment successfully deleted", Response.Status.OK, null);
        }
        return new BodyResponse("Not enough permission", Response.Status.BAD_REQUEST, null);
    }

    public PostCommentsDto fillPostCommentsDto(PostCommentsDto postCommentsDto, PostComments postComment) {
        postCommentsDto.setComment(postComment.getPostContent());
        postCommentsDto.setCreatedAt(postComment.getCreatedAt());
        postCommentsDto.setPostCommentId(postComment.getId());
        postCommentsDto.setPostId(postComment.getPost().getId());

        UserDto userDto = new UserDto();
        userDto.setId(postComment.getUser().getId());
        userDto.setLogin(postComment.getUser().getLogin());

        postCommentsDto.setUser(userDto);
        return postCommentsDto;
    }
}
