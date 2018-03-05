package com.example.demo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String renderFirstPage(@RequestParam Map<String,Object> model, Model viewModel) {
		return "index";
		
	}
	@RequestMapping(value="/ProfilePage", method=RequestMethod.GET)	
	public String renderProfilePage(@RequestParam Map<String,Object> model, Model viewModel) {
			return "ProfilePage";
	}
	
}
