package kz.nis.share.dtos.course;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllCourses {
    private Long id;
    private String title;

    public AllCourses(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
