package com.chatapp.messenger.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/login-success")
    @ResponseBody
    public String login_success() {
        return "로그인 성공";
    }

    @GetMapping("/login-failure")
    @ResponseBody
    public String login_failure() {
        return "로그인 실패";
    }
}
