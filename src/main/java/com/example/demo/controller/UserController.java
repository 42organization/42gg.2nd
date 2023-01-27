package com.example.demo.controller;

import com.example.demo.dto.UserCreateForm;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public  String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Has Error!";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "Password is not same!";
        }

        User user = User.builder()
                .username(userCreateForm.getUsername())
                .password(userCreateForm.getPassword1())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        try {
            userService.create(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signupFailed";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signupFailed";
        }

        return "signup success!";
    }

    @PostMapping("/login")
    public String login(User user) { //?
        System.out.println("LOGIN!!! : " + user.getUsername());
        User member = userService.findByUsername(user.getUsername());

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
