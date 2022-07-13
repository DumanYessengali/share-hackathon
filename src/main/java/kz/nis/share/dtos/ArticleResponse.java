package kz.nis.share.dtos;

import kz.nis.share.entities.Article;
import kz.nis.share.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private String uuid;
    private String fileName;
    private String fileType;
    private long size;
    private String link;

    public ArticleResponse(Article article) {
        setUuid(article.getUuid());
        setFileName(article.getFileName());
        setFileType(article.getFileType());
        setSize(article.getSize());
        setLink(article.getLink());
    }
}
