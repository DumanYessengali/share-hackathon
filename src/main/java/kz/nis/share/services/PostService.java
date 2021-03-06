package kz.nis.share.services;

import kz.nis.share.dtos.*;
import kz.nis.share.entities.*;
import kz.nis.share.repositories.HashtagRepository;
import kz.nis.share.repositories.PostCommentsRepository;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.responses.BodyResponse;
import kz.nis.share.utils.FileNameHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    private final PostCommentsRepository postCommentRepository;
    private final PostImageService postImageService;



    @Transactional
    public void save(String username, PostRequest postRequest) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setPostContent(postRequest.getPostContent());
        post.setUser(user);
        post.setCreatedAt(LocalDate.now());
        Post save = postRepository.save(post);
        for (String s : postRequest.getHashtags()) {
            Hashtag h = new Hashtag();
            h.setTitle(s);
            Hashtag hs = hashtagRepository.save(h);
            save.getHashtags().add(hs);

        }
    }

    @Transactional
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = fillPostDto(new PostDto(), post);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    @Transactional
    public List<PostDto> getAllMyPosts(String username) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        List<Post> posts = postRepository.findAllByUser(user);
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = fillPostDto(new PostDto(), post);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    @Transactional
    public BodyResponse getPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("Post does not exists", Response.Status.BAD_REQUEST, null);
        }
        PostDto postDto = fillPostDto(new PostDto(), post.get());

        return new BodyResponse("Post by id: " + postDto.getId(), Response.Status.OK, postDto);
    }

    public BodyResponse deletePost(String username, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("Post does not exists", Response.Status.BAD_REQUEST, null);
        }
        if (post.get().getUser().getLogin().equals(username)) {
            postRepository.delete(post.get());
            return new BodyResponse("Post successfully deleted", Response.Status.OK, null);
        }
        return new BodyResponse("Not enough permission", Response.Status.BAD_REQUEST, null);
    }

    public PostDto fillPostDto(PostDto postDto, Post post) {
        postDto.setId(post.getId());
        postDto.setPostContent(post.getPostContent());
        postDto.setTitle(post.getTitle());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setPostLikes(post.getLikes().size());

        UserDto userDto = new UserDto();
        userDto.setId(post.getUser().getId());
        userDto.setName(post.getUser().getName());
        userDto.setSurname(post.getUser().getSurname());
        postDto.setUser(userDto);

        List<PostCommentsDto> postCommentsDtos = new ArrayList<>();
        for (PostComments postComments : post.getComments()) {
            PostCommentsDto postCommentsDto = new PostCommentsDto();
            postCommentsDto.setPostId(postComments.getPost().getId());
            postCommentsDto.setCreatedAt(postComments.getCreatedAt());
            postCommentsDto.setComment(postComments.getPostContent());
            postCommentsDto.setPostCommentId(postComments.getId());
            UserDto udto = new UserDto();
            udto.setId(postComments.getUser().getId());
            udto.setName(postComments.getUser().getName());
            udto.setSurname(postComments.getUser().getSurname());
            postCommentsDto.setUser(udto);
            postCommentsDtos.add(postCommentsDto);
        }
        postDto.setPostComments(postCommentsDtos);

        List<HashtagDto> hashtagDtos = new ArrayList<>();
        for (Hashtag hashtag : post.getHashtags()) {
            HashtagDto hashtagDto = new HashtagDto();

            hashtagDto.setId(hashtag.getId());
            hashtagDto.setTitle(hashtag.getTitle());
            hashtagDtos.add(hashtagDto);
        }

        postDto.setHashtags(hashtagDtos);
        return postDto;
    }
}
