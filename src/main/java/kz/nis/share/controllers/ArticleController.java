package kz.nis.share.controllers;

import kz.nis.share.dtos.ArticleResponse;
import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.entities.Article;
import kz.nis.share.entities.Image;
import kz.nis.share.entities.User;
import kz.nis.share.exceptions.ArticleException;
import kz.nis.share.services.ArticleService;
import kz.nis.share.services.ImageService;
import kz.nis.share.services.UserService;
import kz.nis.share.utils.FileNameHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final UserService userService;
    private final ArticleService articleService;

    private FileNameHelper fileHelper = new FileNameHelper();


    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllImageInfo() {

        List<ArticleResponse> imageResponses = articleService.findAllArticleResponse();
        return ResponseEntity.ok().body(imageResponses);
    }


    @PostMapping("/upload")
    public ArticleResponse uploadSingleFile(@RequestParam("file") MultipartFile file) {
        User a = userService.findUserByLogin("timka.amanzhol");
        Article article = Article.buildArticle(file, fileHelper, a);
        articleService.save(article);
        return new ArticleResponse(article);
    }


    @PostMapping("/uploads")
    public List<ArticleResponse> uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> uploadSingleFile(file)).collect(Collectors.toList());
    }


    @GetMapping("/show/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws Exception {
        Article article = getArticleByName(fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(article.getFileType())).body(article.getData());
    }





    public Article getArticleByName(String name) throws Exception {
        Article article = articleService.findByFileName(name);
        if (article == null) {
            throw new ArticleException("Article not found");
        }
        return article;
    }





    public Article getArticleByUuid(String uuid) throws Exception {
        Article article = articleService.findByUuid(uuid);
        if (article == null) {
            throw new ArticleException("Article not found");
        }
        return article;
    }



}
