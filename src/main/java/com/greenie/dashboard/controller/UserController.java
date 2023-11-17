package com.greenie.dashboard.controller;

import com.greenie.dashboard.UserPDFExporter;
import com.greenie.dashboard.model.User;
import com.greenie.dashboard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getHomePage(Model model){
        List<User> userList=new ArrayList<>();
        userList=userService.getAllUsers();
        if (userList.size()!=0)
            model.addAttribute("searchResults",userList);
        model.addAttribute("registerRequest", new User());
        return "home";
    }

    @PostMapping("/")
    public String register(@ModelAttribute User user){
        System.out.println("register request" + user);
        User registeredUser=userService.createUser(user.getName(),user.getEmail(),user.getPhone());
        return "redirect:/";
    }

    @PostMapping("/getid")
    public String getId(@RequestParam(name = "id") Integer id, Model model, HttpServletResponse response) throws IOException {
        User user=userService.finduser(id);
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        UserPDFExporter exporter = new UserPDFExporter(user);
        exporter.export(response);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam(name = "searchType") String searchType
                                    ,@RequestParam(name = "value") String searchText,
                                  Model model) {
        List<User> searchResults = null;
        if(searchType.equals("name"))
            searchResults=userService.findByName(searchText);
        else if(searchType.equals("email"))
            searchResults=userService.findByEmail(searchText);
        else if(searchType.equals("phone"))
            searchResults=userService.findByPhone(searchText);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("registerRequest", new User());
        return "home";
    }
}
