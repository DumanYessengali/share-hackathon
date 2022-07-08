package kz.nis.share.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "post_content")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToMany
    @JoinTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hash_id"))
    private List<Hashtag> hashtagList;
}
