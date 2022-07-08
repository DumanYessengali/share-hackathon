package kz.nis.share.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "user_educations")
@NoArgsConstructor
public class UserEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "major")
    private String major;

    @Column(name = "degree_id")
    @Enumerated(EnumType.ORDINAL)
    private EDegree degree;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}