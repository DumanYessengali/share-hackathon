package kz.nis.share.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleWihtLinkDto {
    private String link;
    private byte[] arr;
}
