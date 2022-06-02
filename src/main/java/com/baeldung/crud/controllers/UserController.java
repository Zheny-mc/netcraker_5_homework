package com.baeldung.crud.controllers;


//import com.baeldung.crud.entities.UserFind;
import com.baeldung.crud.services.ValidUser;
import com.baeldung.crud.thowable.InvalidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.baeldung.crud.entities.User;
import com.baeldung.crud.repositories.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {
    
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }
    
    @PostMapping("/adduser")
    public String addUser(User user, BindingResult result, Model model) throws InvalidEmail {
        if (result.hasErrors()) {
            return "add-user";
        }

	    if (ValidUser.validEmail(user)) {
		    userRepository.save(user);
	    }

        return "redirect:/index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userRepository.save(user);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);

        return "redirect:/index";
    }

//	@GetMapping("/info")
//    public String showInfo() {
//	    User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//	    model.addAttribute("user", user);
//
//	    return "info-user";
//    }

//    @PostMapping("/info")
//    public String getInfoUser(UserFind userFind, BindingResult result, Model model) {
//	    if (result.hasErrors()) {
//		    return "index";
//	    }
//
//	    String filter = userFind.getFirstName();
//    	Iterable<User> listUser;
//
//		if (filter != null && !filter.isEmpty()) {
//			listUser = userRepository.findByFirName(filter);
//		} else {
//			listUser = userRepository.findAll();
//		}
//
//	    model.addAttribute("users", listUser);
//	    return "index";
//    }

}
