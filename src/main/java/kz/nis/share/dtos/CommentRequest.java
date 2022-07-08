package kz.nis.share.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// TODO  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {
    private String postContent;
    private Long postId;
}
