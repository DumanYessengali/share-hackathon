package kz.nis.share.services;

import kz.nis.share.dtos.PostRequest;
import kz.nis.share.entities.Hashtag;
import kz.nis.share.entities.Post;
import kz.nis.share.entities.User;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.responses.BodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocalDate timestamp = LocalDate.from(LocalDateTime.now());

    public void save(String username, PostRequest postRequest) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        Post post = new Post(postRequest.getTitle(), postRequest.getPostContent(), user, timestamp);
        postRequest.getHashtags().forEach(hashtag -> post.addHashtag(new Hashtag(hashtag)));
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public List<Post> getAllMyPosts(String username) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        return postRepository.findAllByUser(user);
    }

    public BodyResponse deletePost(String username, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new BodyResponse("Post does not exists", Response.Status.BAD_REQUEST, null);
        }
        if (post.get().getUser().getLogin().equals(username)) {
            return new BodyResponse("Post successfully deleted", Response.Status.OK, null);
        }
        return new BodyResponse("Not enough permission", Response.Status.BAD_REQUEST, null);
    }
}
