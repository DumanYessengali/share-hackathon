package kz.nis.share.repositories;

import kz.nis.share.entities.Post;
import kz.nis.share.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post,Long> {
    List<Post> findAllByUser(User user);
}