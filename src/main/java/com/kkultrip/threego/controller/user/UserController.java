package com.kkultrip.threego.controller.user;

import com.kkultrip.threego.config.mvc.NavList;
import com.kkultrip.threego.model.Page;
import com.kkultrip.threego.model.User;
import com.kkultrip.threego.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String User(
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "indexSize", required = false) Integer indexSize,
            @RequestParam(value = "searchCondition", required = false) String searchCondition,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model
    ){

        Page page = userService.pageInfo(pageIndex, indexSize, searchCondition, searchKeyword);

        List<User> user = userService.pageList(page);

        model.addAttribute("user",user);
        model.addAttribute("page", page);
        NavList.navUser(model);
        return "user/user";
    }
}
