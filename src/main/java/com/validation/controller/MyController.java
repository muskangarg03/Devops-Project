package com.validation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.validation.entities.LoginData;

import jakarta.validation.Valid;

@Controller
public class MyController {

	@GetMapping("/form")
	public String openForm(Model m) {
		System.out.println("opening form...");
		m.addAttribute("loginData",new LoginData());
		return "form";
	}
	
	
	//handler for process form
	@PostMapping("/process")
	public String processForm(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult result)    
	//@Valid annotation is used to trigger the validations applied on the fields
	//BindingResult is used to store the data that comes after the validation
	{
		
		if(result.hasErrors())  //hasErrors() will check for any error if occurred
		{
			System.out.println(result);
			return "form";        //If any error occurred, it will redirect to the form again.
		}
		
		System.out.println(loginData);
		return "success";
	}
	
}
