package kz.nis.share.controllers;

import kz.nis.share.dtos.JwtRequest;
import kz.nis.share.dtos.JwtResponse;
import kz.nis.share.dtos.LoginRequest;
import kz.nis.share.responses.BodyResponse;
import kz.nis.share.responses.ResponseMessage;
import kz.nis.share.services.UserService;
import kz.nis.share.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());
            System.out.println(1.1);

            authenticationManager.authenticate(authentication);
            System.out.println(1);
        } catch (BadCredentialsException e) {
            System.out.println(2);
            return new ResponseEntity<>(new ResponseMessage("Incorrect username or password", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        List<String> roles = jwtTokenUtil.getRoles(token);

        return ResponseEntity.ok(new JwtResponse(token, roles));
    }

    @PostMapping("/register")
    public BodyResponse register(@RequestBody JwtRequest jwtRequest) {
        return userService.register(jwtRequest);
    }


}
