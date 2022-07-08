package kz.nis.share.repositories;


import kz.nis.share.entities.User;
import kz.nis.share.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
    Optional<UserDetail> findUserDetailByUser(User user);

}
