package com.udemy.backendninja.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.udemy.backendninja.model.UserCredential;

@Controller
public class LoginController {

	public static final Log LOG = LogFactory.getLog(LoginController.class);

	@GetMapping("/")
	public String redirectToLogin() {
		LOG.info("METHOD: redirectToLogin()");
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model,
			@RequestParam(name = "error", defaultValue = "", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("METHOD: showLoginForm() -- PARAMS: error='" + error.toString() + "', logout='" + logout + "'");
		model.addAttribute("userCredentials", new UserCredential());
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		LOG.info("Returning to login view");
		return "login";
	}

	@PostMapping("/logincheck")
	public String loginCheck(@ModelAttribute("userCredentials") UserCredential userCredential) {
		LOG.info("METHOD: loginCheck() -- PARAMS: userCredential='" + userCredential.toString() + "'");
		if (userCredential.getUsername().equals("user") && userCredential.getPassword().equals("user")) {
			LOG.info("Returning to contact view");
			return "redirect:/contacts/showcontacts";
		}
		LOG.info("Returning to login?error view");
		return "redirect:/login?error=error";
	}
}
