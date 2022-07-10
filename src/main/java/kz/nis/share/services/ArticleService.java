package kz.nis.share.services;

import kz.nis.share.dtos.ArticleResponse;
import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.entities.Article;
import kz.nis.share.entities.Image;
import kz.nis.share.repositories.ArticleRepository;
import kz.nis.share.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


    public Article save(Article article) throws NullPointerException {
        if (article == null)
            throw new NullPointerException("Article Data NULL");
        return articleRepository.save(article);
    }


    public Article findByFileName(String fileName) {
        return this.articleRepository.findByFileName(fileName);
    }

    public Article findByFileTitle(String title) {
        return this.articleRepository.findByTitleContaining(title);
    }


    public Article findByUuid(String uuid) {
        return this.articleRepository.findByUuid(uuid);
    }


    public List<ArticleResponse> findAllArticleResponse() {
        return this.articleRepository.findAllArticleResponse();
    }

}
