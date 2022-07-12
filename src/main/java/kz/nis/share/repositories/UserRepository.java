package kz.nis.share.repositories;


import kz.nis.share.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {



    Optional<User> findUserByLogin(String username);

    Optional<User> findUserById(Long userId);

    User findUserBySurnameAndName(String surname, String name);

}
