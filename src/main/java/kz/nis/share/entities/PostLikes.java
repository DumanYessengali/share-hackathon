package kz.nis.share.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "liked_post")
@NoArgsConstructor
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "liked")
    private Integer liked;

    //create table liked_post
    //(
    //    id BIGSERIAL not null primary key,
    //    user_id bigint not null,
    //    post_id bigint not null,
    //    liked integer not null,
    //    foreign key (user_id) references users(id),
    //    foreign key (post_id) references post(id)
    //);

}
