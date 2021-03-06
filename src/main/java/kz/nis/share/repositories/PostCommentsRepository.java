package kz.nis.share.repositories;

import kz.nis.share.entities.Post;
import kz.nis.share.entities.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments, Long> {
    List<PostComments> findAllByPost(Post post);
}
