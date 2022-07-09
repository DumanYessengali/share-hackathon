package kz.nis.share.entities;

import com.sun.istack.NotNull;
import kz.nis.share.dtos.UserDetailRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
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
    private LocalDate birthday;

    @Column(name = "subject")
    private String subject;

    @Column(name = "created_at")
    private LocalDate createdDate;

    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return job + createdDate + user;
    }

    public UserDetail(UserDetailRequest userDetailRequest, User user) {
        this.job = userDetailRequest.getJob();
        this.birthday = userDetailRequest.getBirthday();
        this.subject = userDetailRequest.getSubject();
        this.createdDate = LocalDate.now();
        this.user = user;
    }

    public void setDetail(UserDetailRequest userDetailRequest) {
        setJob(userDetailRequest.getJob());
        setBirthday(userDetailRequest.getBirthday());
        setSubject(userDetailRequest.getSubject());
    }

    public void setJob(@NotNull String job) {
        this.job = job;
        this.modifiedAt = LocalDate.now();

    }

    public void setBirthday(@NotNull LocalDate birthday) {
        this.birthday = birthday;
        this.modifiedAt = LocalDate.now();
    }

    public void setSubject(@NotNull String subject) {
        this.subject = subject;
        this.modifiedAt = LocalDate.now();
    }
}
