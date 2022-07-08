package kz.nis.share.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post_likes")
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_content")
    private String postContent;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> userId;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Post> postId;


    @Column(name = "created_at")
    private Date createdAt;

}
