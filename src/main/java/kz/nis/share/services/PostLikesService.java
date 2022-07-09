package kz.nis.share.services;

import kz.nis.share.dtos.UserDto;
import kz.nis.share.entities.Post;
import kz.nis.share.entities.PostLikes;
import kz.nis.share.entities.User;
import kz.nis.share.repositories.PostLikesRepository;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.responses.BodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikesService {
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public BodyResponse likeThePost(Long postId, String username) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("post does not exist", Response.Status.BAD_REQUEST, null);
        }

        Optional<User> user = userRepository.findUserByLogin(username);
        if (user.isEmpty()) {
            return new BodyResponse("user does not exist", Response.Status.BAD_REQUEST, null);
        }

        Optional<PostLikes> postLike = postLikesRepository.findByPostAndUser(post.get(), user.get());
        if (postLike.isEmpty()) {
            PostLikes postLikes = new PostLikes();
            postLikes.setLiked(1);
            postLikes.setPost(post.get());
            postLikes.setUser(user.get());
            postLikesRepository.save(postLikes);
            return new BodyResponse("liked post", Response.Status.OK, null);
        }
        return new BodyResponse("user already liked", Response.Status.BAD_REQUEST, null);
    }

    public BodyResponse getPostLikes(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("post does not exist", Response.Status.BAD_REQUEST, null);
        }

        List<PostLikes> postLikes = postLikesRepository.findAllByPost(post.get());
        return new BodyResponse("Numbers of like", Response.Status.OK, postLikes.size());
    }

    public BodyResponse removeLikeFromPost(String username, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("post does not exist", Response.Status.BAD_REQUEST, null);
        }

        Optional<User> user = userRepository.findUserByLogin(username);
        if (user.isEmpty()) {
            return new BodyResponse("user does not exist", Response.Status.BAD_REQUEST, null);
        }

        Optional<PostLikes> postLike = postLikesRepository.findByPostAndUser(post.get(), user.get());
        if (postLike.isEmpty()) {
            return new BodyResponse("post not liked", Response.Status.BAD_REQUEST, null);
        }
        postLikesRepository.delete(postLike.get());
        return new BodyResponse("like deleted", Response.Status.OK, null);
    }

    public BodyResponse postLikedUsers(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("post does not exist", Response.Status.BAD_REQUEST, null);
        }

        List<PostLikes> postLikes = postLikesRepository.findAllByPost(post.get());
        List<UserDto> userDtoList = new ArrayList<>();
        for (PostLikes postLike : postLikes) {
            UserDto  userDto = new UserDto();
            userDto.setId(postLike.getUser().getId());
            userDto.setName(postLike.getUser().getName());
            userDto.setSurname(postLike.getUser().getSurname());
            userDtoList.add(userDto);
        }

        return new BodyResponse("post liked users", Response.Status.OK, userDtoList);
    }
}
