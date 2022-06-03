package com.baeldung.crud.controllers;


//import com.baeldung.crud.entities.UserFind;
import com.baeldung.crud.services.FileService;
import com.baeldung.crud.services.ValidUser;
import com.baeldung.crud.thowable.InvalidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.baeldung.crud.entities.User;
import com.baeldung.crud.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class UserController {
    
    private final UserRepository userRepository;
	private final FileService fileService;

    @Autowired
    public UserController(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
	    this.fileService = fileService;
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
	@GetMapping("/uploadFile")
	public String index() {
		return "upload";
	}

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

		User user = fileService.createUserFromFile(
				fileService.readFromInputStream(file.getInputStream()));

		userRepository.save(user);

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/index";
	}

}
