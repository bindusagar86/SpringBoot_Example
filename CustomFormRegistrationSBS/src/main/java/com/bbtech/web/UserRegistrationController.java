package com.bbtech.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbtech.model.User;
import com.bbtech.service.UserService;
import com.bbtech.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private UserService service;
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}
	
	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
					BindingResult result) {
		System.out.println("UserDto: "+userDto);
		User existingUser=service.findByEmail(userDto.getEmail());
		System.out.println("existing user: "+existingUser);
		if(existingUser!=null) {
			result.rejectValue("email",null, "There is already an account registered with that email.");
		}
		if(result.hasErrors()) {
			return "registration";
		}
		service.save(userDto);
		return "redirect:/registration?success";
	}
}
