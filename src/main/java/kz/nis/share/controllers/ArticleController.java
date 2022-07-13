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
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
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
    public ArticleResponse uploadSingleFile(Principal principal, @RequestParam("file") MultipartFile file, String title) {
        User a = userService.findUserByLogin(principal.getName());
        Article article = Article.buildArticle(file, fileHelper, a, title);
        articleService.save(article);
        return new ArticleResponse(article);
    }


//    @PostMapping("/uploads")
//    public List<ArticleResponse> uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files).stream().map(file -> uploadSingleFile(file)).collect(Collectors.toList());
//    }


    @GetMapping("/show/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws Exception {
        Article article = getArticleByName(fileName);
        File file = null;
        String parsedText = null;
        byte[] bytes = null;
        try {

            file = new File("articles/" + article.getFileName());
            boolean newFile = file.createNewFile();

            OutputStream os = new FileOutputStream(file);


            os.write(article.getData());


            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            System.out.println("Successfully"
                    + " byte inserted");

            os.close();
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Display exception on console
            System.out.println("Exception: " + e);
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf(article.getFileType())).body(parsedText.getBytes());
    }

    @GetMapping("/find/{title}")
    public ResponseEntity<byte[]> getFileByTitle(@PathVariable String title)  {
        Article article = getArticleByTitle(title);
        File file = null;
        String parsedText = null;
        byte[] bytes = null;
        try {

            file = new File("/home/amanzhol.temirbolat/IdeaProjects/shareFinal/articles/" + article.getFileName());
            boolean newFile = file.createNewFile();

            OutputStream os = new FileOutputStream(file);


            os.write(article.getData());


            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            System.out.println("Successfully"
                    + " byte inserted");

            os.close();
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Display exception on console
            System.out.println("Exception: " + e);
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf(article.getFileType())).body(parsedText.getBytes());
    }





    public Article getArticleByName(String name) {
        Article article = articleService.findByFileName(name);
        if (article == null) {
            throw new ArticleException("Article not found");
        }
        return article;
    }

    public Article getArticleByTitle(String title)  {
        Article article = articleService.findByFileTitle(title);
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
