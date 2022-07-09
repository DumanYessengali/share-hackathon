package kz.nis.share.dtos;

import kz.nis.share.entities.Hashtag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String postContent;
    private List<String> hashtags;
}
