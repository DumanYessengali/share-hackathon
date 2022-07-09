package kz.nis.share.controllers;

import kz.nis.share.exceptions.UserDetailNotFound;
import kz.nis.share.exceptions.UserNotFound;
import kz.nis.share.repositories.UserRepository;
import kz.nis.share.services.UserDetailService;
import kz.nis.share.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user-details")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @GetMapping
    public ResponseEntity<?> getUserDetail(Principal principal) {
        try {
            return ResponseEntity.ok(userDetailService.getUserDetail(principal.getName()));
        } catch (UserNotFound e) {
            return ResponseEntity.badRequest().body("Cant find user");
        } catch (UserDetailNotFound e) {
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> setUserDetail() {
        return ResponseEntity.ok("");
    }


}
