package kz.nis.share.dtos;

import kz.nis.share.entities.Hashtag;
import kz.nis.share.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String postContent;
    private UserDto user;
    private List<PostCommentsDto> postComments;
    private Integer postLikes;
    private LocalDate createdAt;
    private List<HashtagDto> hashtags;
}
