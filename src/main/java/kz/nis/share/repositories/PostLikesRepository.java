package kz.nis.share.repositories;

import kz.nis.share.entities.Post;
import kz.nis.share.entities.PostLikes;
import kz.nis.share.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByPostAndUser(Post post, User user);

    List<PostLikes> findAllByPost(Post post);
}
