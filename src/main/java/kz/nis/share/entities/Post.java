package kz.nis.share.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "post")
    private List<PostComments> comments;

    @OneToMany(mappedBy = "post")
    private List<PostLikes> likes;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post_hashtags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<Hashtag> hashtags = new HashSet<>();

    public void addHashtag(Hashtag hashtag) {
        this.getHashtags().add(hashtag);
        hashtag.getPosts().add(this);
    }

    public void removeBook(Hashtag hashtag) {
        this.getHashtags().remove(hashtag);
        hashtag.getPosts().remove(this);
    }


    public Post(String title, String postContent, User user, LocalDate createdAt) {
        this.title = title;
        this.postContent = postContent;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
