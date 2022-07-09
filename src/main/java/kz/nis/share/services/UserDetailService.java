package kz.nis.share.services;

import kz.nis.share.entities.User;
import kz.nis.share.entities.UserDetail;
import kz.nis.share.exceptions.UserDetailNotFound;
import kz.nis.share.exceptions.UserNotFound;
import kz.nis.share.repositories.UserDetailRepository;
import kz.nis.share.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    public UserDetail getUserDetail(String username) throws UserNotFound, UserDetailNotFound {
        User user =
                userRepository
                        .findUserByLogin(username)
                        .orElseThrow(() -> new UserNotFound("User not found in UserDetailService"));
        return userDetailRepository.findUserDetailByUser(user)
                .orElseThrow(() -> new UserDetailNotFound("UserDetail not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
