package kz.nis.share.repositories;

import kz.nis.share.dtos.user.profile.UserProfileEducation;
import kz.nis.share.entities.User;
import kz.nis.share.entities.UserEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEducationRepository extends JpaRepository<UserEducation, Long> {
    UserProfileEducation findUserEducationByUser(User user);
    List<UserEducation> findAllByUser(User user);

}
