package kz.nis.share.repositories;


import kz.nis.share.dtos.user.profile.UserProfileInfo;
import kz.nis.share.entities.User;
import kz.nis.share.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserDetail, Long> {
    UserProfileInfo findUserInfoByUser(User user);
    Optional<UserDetail> findUserDetailByUser(User user);

}
