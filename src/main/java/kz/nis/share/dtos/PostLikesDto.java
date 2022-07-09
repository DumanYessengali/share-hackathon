package kz.nis.share.dtos;

import kz.nis.share.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikesDto {
    private Long id;
    private UserDto user;
    private Integer liked;
}
