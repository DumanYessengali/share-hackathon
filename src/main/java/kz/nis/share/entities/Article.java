package kz.nis.share.entities;

import kz.nis.share.utils.FileNameHelper;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "size")
    private long size;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Lob
    @Column(name = "data")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    public static Article build() {
        String uuid = UUID.randomUUID().toString();
        Article article = new Article();
        article.setUuid(uuid);
        article.setCreatedDate(LocalDate.now());
        return article;
    }

    @Transient
    public void setFiles(MultipartFile file) {
        setFileType(file.getContentType());
        setSize(file.getSize());
    }

    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = Image.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }


    @Transient
    public static Article buildArticle(MultipartFile file, FileNameHelper helper, User user) {
        String fileName = helper.generateDisplayName(file.getOriginalFilename());

        Article article = Article.build();
        article.setFileName(fileName);
        article.setFiles(file);
        article.setUser(user);


        try {

            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            article.setData(inputStream.readAllBytes());
            //   image.setData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return article;
    }


}
