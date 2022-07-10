package kz.nis.share.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "lesson_number")
    private String lessonNumber;


    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "lesson")
    private List<CommentLesson> commentLessonList;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;




}
