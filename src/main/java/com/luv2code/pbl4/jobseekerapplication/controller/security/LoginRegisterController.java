package com.luv2code.pbl4.jobseekerapplication.controller.security;

import com.luv2code.pbl4.jobseekerapplication.entity.User;
import com.luv2code.pbl4.jobseekerapplication.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class LoginRegisterController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginRegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

        @GetMapping("/thong-tin-ca-nhan")
        public String showProfilePage(Model model) {
            User user = userService.findByEmail(getCurrentUsername());

            if(user == null) {
                return "redirect:/dang-nhap";
            }else {
                model.addAttribute("user", user);
                String inforesult = (String) model.getAttribute("info");
                if(inforesult != null) {
                    model.addAttribute("info", inforesult);
                }
            }
            return "security/profile";
        }


        @PostMapping("/thay-doi-thong-tin-ca-nhan")
        public String updateProfile(@RequestParam(required = true) String fullname,
                                    @RequestParam(required = true) String email,
                                    RedirectAttributes redirectAttributes) {
            User user = userService.findByEmail(email);

            if(user == null) {
                return "redirect:/dang-nhap";
            }else {
                user.setUsername(fullname);
                user.setEmail(email);

                userService.update(user);

            }
            redirectAttributes.addFlashAttribute("inforChangeSuccess", "Cập nhật thông tin thành công!");
            return "redirect:/thong-tin-ca-nhan";
        }

        @GetMapping("/thay-doi-mat-khau")
        public String showChangePasswordPage(Model model) {
            return "security/change-password";
        }


        @PostMapping("/thay-doi-mat-khau")
        public String changePassword(@RequestParam(required = true) String oldPassword,
                                     @RequestParam(required = true) String newPassword,
                                     RedirectAttributes redirectAttributes) {
            User user = userService.findByEmail(getCurrentUsername());

            if(user == null) {
                return "redirect:/dang-nhap";
            }else {
                if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
                    redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng!");
                    return "redirect:/thay-doi-mat-khau";
                }else {

                    user.setPassword(newPassword);
                    userService.save(user);
                }
            }
            redirectAttributes.addFlashAttribute("inforChangeSuccess", "Cập nhật mật khẩu thành công!");
            return "redirect:/thong-tin-ca-nhan";
        }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername(); // Lấy username
            } else {
                return principal.toString(); // Trường hợp sử dụng chuỗi username
            }
        }
        return null;
    }
}
