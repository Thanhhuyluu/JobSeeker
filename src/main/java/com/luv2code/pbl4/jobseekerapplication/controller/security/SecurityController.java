package com.luv2code.pbl4.jobseekerapplication.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "security/access-denied";
    }
}
