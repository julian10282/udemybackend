package com.udemy.backendninja.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	
	public static final Log LOG = LogFactory.getLog(ContactController.class);
	
	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactServce;
	
	@GetMapping("/contactform")
	public String redirectContactForm(@RequestParam(name="id", required= false) int id,
			Model model) {
		ContactModel contactModel = new ContactModel();
		if (id != 0) {
			contactModel = contactServce.findContactById(id);
		}
		model.addAttribute("contactModel", contactModel);
		return "contactform";
	}
	
	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/contacts/showcontacts";
	}
	
	@PostMapping("/addcontact")
	public ModelAndView addContact(@ModelAttribute(name="contactModel") ContactModel contactModel) {
		LOG.info("METHOD: addContact() -- PARAMS: userCredential='" + contactModel.toString() + "'");
		int result;
		ContactModel contactModel2 = contactServce.addContact(contactModel);
		if (contactModel2 != null) {
			if (contactModel.getId() != 0) {
				result = 3;
			} else {
				result = 1;
			}
		} else {
			result = 0;
		}
		return showContacts().addObject("result", result);
	}
	
	@GetMapping("/showcontacts")
	public ModelAndView showContacts() {
		ModelAndView modelAndView = new ModelAndView("contacts");
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		modelAndView.addObject("contacts", contactServce.getAllContacts());
		modelAndView.addObject("username", user.getUsername());
		
		return modelAndView;
	}
	
	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name="id", required= true) int id) {
		contactServce.removeContact(id);
		return showContacts().addObject("result", 2);
	}

}
