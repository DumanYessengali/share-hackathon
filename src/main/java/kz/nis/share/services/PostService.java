package kz.nis.share.services;

import kz.nis.share.dtos.PostRequest;
import kz.nis.share.entities.Hashtag;
import kz.nis.share.entities.Post;
import kz.nis.share.entities.User;
import kz.nis.share.repositories.HashtagRepository;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.responses.BodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public void save(String username, PostRequest postRequest) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setPostContent(postRequest.getPostContent());
        post.setUser(user);
        post.setCreatedAt(LocalDate.now());
        Post save = postRepository.save(post);
        System.out.println("giii");
        for (String s : postRequest.getHashtags()) {
            Hashtag h = new Hashtag();
            h.setTitle(s);
            Hashtag hs = hashtagRepository.save(h);
            save.getHashtags().add(hs);

        }




//
//        List<Hashtag> hashtags = new ArrayList<>();
//        for(String s : postRequest.getHashtags()) {
//            Hashtag hashtag = new Hashtag();
//            hashtag.setTitle(s);
//            hashtags.add(hashtag);
//            hashtagRepository.save(hashtag);
//
//            List<Post> posts = hashtag.getPosts();
//            posts.add(post);
//        }
//
//
//        post.setHashtags(hashtags);
//
//
//        postRepository.save(post);
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
            postRepository.delete(post.get());
            return new BodyResponse("Post successfully deleted", Response.Status.OK, null);
        }
        return new BodyResponse("Not enough permission", Response.Status.BAD_REQUEST, null);
    }
}
