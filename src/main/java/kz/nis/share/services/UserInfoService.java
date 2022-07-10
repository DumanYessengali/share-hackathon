package kz.nis.share.services;

import kz.nis.share.dtos.*;
import kz.nis.share.entities.User;
import kz.nis.share.entities.UserDetail;
import kz.nis.share.entities.UserEducation;
import kz.nis.share.repositories.UserEducationRepository;
import kz.nis.share.repositories.UserInfoRepository;
import kz.nis.share.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserEducationRepository userEducationRepository;
    private final UserRepository userRepository;

    public UserProfileResponse getUserInfo(String username) {
        User user = userRepository
                        .findUserByLogin(username).orElseThrow();

        UserDetail userInfo = userInfoRepository.findUserDetailByUser(user).orElseThrow();
        List<UserEducation> userEducationList = userEducationRepository.findAllByUser(user);

        return new UserProfileResponse(userInfo, userEducationList);
    }

    public void addToUserInfo(String username, UserDetailRequest userDetailRequest) {
        User user = userRepository
                .findUserByLogin(username).orElseThrow();
        System.out.println(user);
        UserDetail userDetail = null;
        if (user.getUserDetail() == null) {
            userDetail = new UserDetail(userDetailRequest, user);
        } else {
            user.getUserDetail().setDetail(userDetailRequest);
        }
        try {
            userInfoRepository.save(userDetail);
            userRepository.save(user);
        } catch (Exception e) {

        }
    }

    public void addToUserEducation(String username, List<UserEducationDto> userEducationDtoList ) {
        User user = userRepository
                .findUserByLogin(username).orElseThrow();

        for (UserEducationDto userEducationDto : userEducationDtoList) {
            UserEducation userEducation = new UserEducation(userEducationDto, user);
            userEducationRepository.save(userEducation);
            userRepository.save(user);
        }
    }


}
