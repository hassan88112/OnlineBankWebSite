package com.hassan.OnlineBanking.Controller;


import com.hassan.OnlineBanking.Service.UserService;
import com.hassan.OnlineBanking.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model){

        User user=new User();
        model.addAttribute("user",user);

        return "signup";
    }
    @PostMapping("/signup")
    public String signupPost(@ModelAttribute ("user") User user, Model model){

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
       //     Set<UserRole> userRoles = new HashSet<>();
       //     userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

            userService.save(user);

            return "redirect:/";
        }
    }


}