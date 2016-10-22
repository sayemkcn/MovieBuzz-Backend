package net.toracode.moviedb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.services.PersonService;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPersonPage() {
		return "person/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPerson(@ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
		if (bindingResult == null) {
			return null;
		}
		personService.save(person);
		model.addAttribute("message", "Successfully saved " + person.getName());
		return "person/create";
	}

}
