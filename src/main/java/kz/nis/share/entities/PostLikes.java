package kz.nis.share.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post_likes")
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Post> postId;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> userId;

    @Column(name = "liked")
    private Short liked;

}
