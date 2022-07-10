package kz.nis.share.repositories;

import kz.nis.share.entities.Article;
import kz.nis.share.entities.CommentLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLessonRepository extends JpaRepository<CommentLesson, Long> {
}
