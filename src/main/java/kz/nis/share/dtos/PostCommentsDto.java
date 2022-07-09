package kz.nis.share.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class PostCommentsDto {
    private Long postCommentId;
    private String comment;
    private UserDto user;
    private Long postId;
    private LocalDate createdAt;

}
