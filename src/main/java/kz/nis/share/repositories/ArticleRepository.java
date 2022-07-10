package kz.nis.share.repositories;

import kz.nis.share.dtos.ArticleResponse;
import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.entities.Article;
import kz.nis.share.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByFileName(String fileName);


    Article findByTitleContaining(String title);

    Article findByUuid(String uuid);

    @Query(value = "select new kz.nis.share.dtos.ArticleResponse(a.uuid, a.fileName, a.fileType, a.size) from Article a")
    List<ArticleResponse> findAllArticleResponse();
}
