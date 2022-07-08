package kz.nis.share.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post_comments")
@NoArgsConstructor
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    @Column(name = "created_at")
    private LocalDate createdAt;

    //create table post_comments
    //(
    //    id BIGSERIAL not null primary key,
    //    user_id bigint not null,
    //    post_id bigint not null,
    //    content varchar(500) not null,
    //    created_at date not null,
    //    foreign key (user_id) references users(id),
    //    foreign key (post_id) references post(id)
    //);

}
