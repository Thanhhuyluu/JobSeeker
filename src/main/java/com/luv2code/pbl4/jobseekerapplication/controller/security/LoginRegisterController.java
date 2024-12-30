package com.luv2code.pbl4.jobseekerapplication.controller.security;

import com.luv2code.pbl4.jobseekerapplication.entity.User;
import com.luv2code.pbl4.jobseekerapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class LoginRegisterController {
        UserService userService;

        public LoginRegisterController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/dang-nhap")
        public String showLoginPage() {
            return "security/login";
        }

        @GetMapping("/dang-ki")
        public String showRegisterPage() {
            return "security/register";
        }

        @PostMapping("/dang-ki")
        public String register(@RequestParam(required = true) String fullname,
                               @RequestParam(required = true) String password,
                               @RequestParam(required = true) String email,
                               Model model) {

            User user = userService.findByEmail(email);
            String error = null;
            if(user != null) {
                error = "Email đã tồn tại";
            }else {
                user = new User(fullname, password, email, true);

                userService.save(user);
            }


            if(error != null) {
                model.addAttribute("error", error);
                return "security/register";
            }


            return "security/login";
        }
}
