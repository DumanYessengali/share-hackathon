package kz.nis.share.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "user_details")
@NoArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "job")
    private String job;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "subject")
    private String subject;

    @Column(name = "created_at")
    private LocalDate createdDate;

    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
