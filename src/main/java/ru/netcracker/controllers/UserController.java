package ru.netcracker.controllers;


import ru.netcracker.entities.UserBeforeFind;
import ru.netcracker.entities.UserFind;
import ru.netcracker.services.FileService;
import ru.netcracker.services.ValidUser;
import ru.netcracker.thowable.InvalidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.netcracker.entities.User;
import ru.netcracker.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//------------------------------------------------------------------------------------------
	@GetMapping("/search")
    public String showInfo() {
	    return "info-user";
    }

    @PostMapping("/search")
    public String getInfoUser(UserFind userFind, BindingResult result, Model model) {

	    String fName = userFind.getFirstName();
	    String mName = userFind.getMidleName();
    	List<UserBeforeFind> listUser = new ArrayList<>();
        List<User> sourceUsers = null;

		if (fName != null && !fName.isEmpty() && mName != null && !mName.isEmpty()) {
            sourceUsers = userRepository.findByFirNameAndMidlName(fName, mName);
		} else {
            sourceUsers = userRepository.findAll();
		}

        for (var user: sourceUsers) {
            listUser.add(new UserBeforeFind(user));
        }

	    model.addAttribute("list_user", listUser);
	    return "info-user";
    }
    //------------------------------------------------------------------------
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
