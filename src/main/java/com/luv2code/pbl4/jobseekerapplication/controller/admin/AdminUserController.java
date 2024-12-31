package com.luv2code.pbl4.jobseekerapplication.controller.admin;


import com.luv2code.pbl4.jobseekerapplication.dto.JobsOfIndustry;
import com.luv2code.pbl4.jobseekerapplication.entity.User;
import com.luv2code.pbl4.jobseekerapplication.service.IndustryService;
import com.luv2code.pbl4.jobseekerapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    private final UserService userService;
    private final IndustryService industryService;

    @Autowired
    public AdminUserController(UserService userService, IndustryService industryService) {
        this.userService = userService;
        this.industryService = industryService;
    }

    @GetMapping("/nguoi-dung")
    public String getUsers(@RequestParam(defaultValue = "", required = false) String search,
                           @RequestParam(defaultValue = "1", required = false) int pageNo,
                           @RequestParam(defaultValue = "10", required = false) int pageSize,
                           Model model) {

        loadNguoiDung(search,pageNo,pageSize,model);
        return "admin/UserList";
    }


    @GetMapping("/load-nguoi-dung")
    @ResponseBody
    public String loadNguoiDung(@RequestParam(defaultValue = "", required = false) String search,
                                @RequestParam(defaultValue = "1", required = false) int pageNo,
                                @RequestParam(defaultValue = "10", required = false) int pageSize,
                                Model model) {

        Page<User> page = userService.searchUsers(search,pageNo, pageSize);
        List<User> users = page.getContent();
        int pageNum = page.getTotalPages();
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", pageNo);

        model.addAttribute("mode", "MODE_USERS");
        String result = "";
        result += "<div class=\"row\">\n" +
                "                <div class=\"add-new-record-button-wrapper col l-12\">\n" +
                "                    <a href=\"them-nguoi-dung\" class=\"add-new-record-button\">\n" +
                "                        <i class=\"add-new-record-button-icon fa-solid fa-plus\"></i>\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "\n" +
                "\n" +
                "            <div class=\"row\">\n" +
                "\n" +
                "                <div class=\"main-content-table-wrapper\">\n" +
                "                    <table class=\"main-content-table\">\n" +
                "                        <tr>\n" +
                "                            <th>Mã </th>\n" +
                "                            <th>Tên đầy đủ</th>\n" +
                "                            <th>Email</th>\n" +
                "                            <th>Tình trạng</th>\n" +
                "                            <th>Thao tác</th>\n" +
                "                        </tr>";

        for (User user : users) {
            result += "<tr>\n" +
                    "                            <td>\n" +
                    "                                <p class=\"main-content-table-text\"> " + user.getUserId()+"</p>\n" +
                    "                            </td>\n" +
                    "                            <td>\n" +
                    "                                <p class=\"main-content-table-text\">"+ user.getUsername()+"</p>\n" +
                    "                            </td>\n" +
                    "                            <td>\n" +
                    "                                <p class=\"main-content-table-text\">"+ user.getEmail() +"</p>\n" +
                    "                            </td>\n" +
                    "                            <td>\n" +
                    "                                <p class=\"main-content-table-text\">"+(user.getActive() == true ? "Hoạt động" : "Bị khóa") +"</p>\n" +
                    "                            </td>\n" +
                    "                            <td class=\"table-action-button-wrapper\">\n" +
                    "                                <a class=\"table-action-button table-action-button--show\"\n" +
                    "                                     href=\"xem-chi-tiet-nguoi-dung?userId="+user.getUserId()+"\">\n" +
                    "                                    <i class=\"fa-solid fa-circle-info\"></i>\n" +
                    "                                </a>\n" +
                    "                                <a class=\"table-action-button table-action-button--change\"\n" +
                    "                                    th:href=\"sua-thong-tin-nguoi-dung?userId="+user.getUserId()+"\">\n" +
                    "                                    <i class=\"fa-solid fa-pen\"></i>\n" +
                    "                                </a>\n" +
                    "                                <div\n" +
                    "                                        class=\"table-action-button table-action-button--delete\"\n" +
                    "                                        data-url=\"khoa-tai-khoan-nguoi-dung?currentPage="+pageNo+"&userId="+user.getUserId()+"\"\n" +
                    "                                        data-message=\""+(user.getActive() == true ? "Bạn có chắc chắn muốn khóa tài khoản này?" : "Bạn có chắc chắn muốn mở khóa tài khoản này?")+"\"\"\n" +
                    "                                        onclick=\"handleDelete(this)\">\n" +
                    "                                    <i class=\"fa-solid fa-lock\"></i>\n" +
                    "                                </div>\n" +
                    "\n" +
                    "\n" +
                    "                            </td>\n" +
                    "                        </tr>";
        }
        result += "       </table>\n" +
                "<div class=\"app-main-navigate\">\n" +
                "                                <ul  class=\"app-main-navigate-list\">\n" +


                ((pageNo > 1) ?
                        ("                                    <li class=\"app-main-navigate-item\">\n" +
                                "\n" +
                                "                                        <button  class=\"app-main-navigate-item-btn\" value=\"/admin/load-nguoi-dung?pageNo=" + (pageNo - 1) + "\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-left\"></i>\n" +
                                "                                        </button>\n" +
                                "                                    </li>\n")

                        :

                        "");
        for (int i = 1; i <= pageNum; i++) {
            result +=
                    "                                    <li" +
                            "                                        class=\"app-main-navigate-item " + ((pageNo == i) ? "active\" " : "\" ") + ">\n" +
                            "                                        <button class=\"app-main-navigate-item-btn\" value=\"/admin/load-nguoi-dung?pageNo=" + i+ "\">" + i + "</button>\n" +
                            "                            </li>\n";

        }
        result +=
                ((pageNo < pageNum) ?
                        "<li class=\"app-main-navigate-item\">\n" +
                                "                                        <button class=\"app-main-navigate-item-btn\" value=\"/admin/load-nguoi-dung?pageNo=" + (pageNo + 1)  + "\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-right\"></i>\n" +
                                "                                        </button>\n" +
                                "</li>\n"

                        :
                        "")
                        +
                        "                                </ul>\n" +
                        "                            </div>";


        result +=
                "                </div>\n" +
                        "            </div>";

        return result;
    }

    @GetMapping("/xem-chi-tiet-nguoi-dung")
    public String xemChiTiet(@RequestParam int userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "admin/UserDetail";
    }

    @GetMapping("/sua-thong-tin-nguoi-dung")
    public String suaThongTin(@RequestParam int userId, @RequestParam int currentPage, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", currentPage);
        return "admin/UserForm";
    }

    @PostMapping("/sua-thong-tin-nguoi-dung")
    public String suaThongTin(@RequestParam int userId, @RequestParam String username, @RequestParam int currentPage) {
        User user = userService.findById(userId);
        user.setUsername(username);
        userService.update(user);
        return "redirect:/admin/nguoi-dung";
    }

    @GetMapping("/khoa-tai-khoan-nguoi-dung")
    public String khoaTaiKhoan(@RequestParam int userId, @RequestParam int currentPage) {
        User user = userService.findById(userId);
        user.setActive(!user.getActive());
        userService.save(user);
        return "redirect:/admin/nguoi-dung?pageNo=" + currentPage;
    }


    @GetMapping("/them-nguoi-dung")
    public String themNguoiDung(Model model) {
        return "admin/UserFormForAdd";
    }


    @PostMapping("/them-nguoi-dung")
    public String them(@RequestParam(required = true) String fullname,
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
            return "admin/UserFormForAdd";
        }


        return "redirect:/admin/nguoi-dung";
    }


//    @GetMapping("/dashboard")
//    public String dashboard() {
//
//        return "admin/Dashboard";
//    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        // Dữ liệu cho đồ thị
        LocalDate sixDaysAgo = LocalDate.now().minusDays(6);
        List<JobsOfIndustry> jobCounts = industryService.countJobsOfIndustry(sixDaysAgo);

        model.addAttribute("jobCounts", jobCounts);
        model.addAttribute("mode", "MODE_DASHBOARD");

        return "admin/Dashboard"; // Tên file template (resources/templates/admin/dashboard.html)
    }


    @GetMapping("/thu-thap-dulieu")
    public String thuThapDuLieu() {
        return "admin/CrawlPage";
    }
}
