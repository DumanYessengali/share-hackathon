package kz.nis.share.controllers;

import kz.nis.share.dtos.UserDetailRequest;
import kz.nis.share.dtos.UserEducationDto;
import kz.nis.share.exceptions.UserDetailNotFound;
import kz.nis.share.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<?> getUserProfile(Principal principal) {
        return ResponseEntity.ok().body(userInfoService.getUserInfo(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> setUserDetail(Principal principal, @RequestBody UserDetailRequest userDetailRequest) {
        try {
            System.out.println(userDetailRequest);
            userInfoService.addToUserInfo(principal.getName(), userDetailRequest);
            return ResponseEntity.ok("Detail to user successfully added");
        } catch (Exception e) {
            return ResponseEntity.ok("User detail not changed");
        }
    }

    @PostMapping("/education")
    public ResponseEntity<?> setUserEducation(Principal principal, @RequestBody List<UserEducationDto> userEducationDtoList) {
        userInfoService.addToUserEducation(principal.getName(), userEducationDtoList);
        return ResponseEntity.ok("Detail to user successfully added");
    }

}

