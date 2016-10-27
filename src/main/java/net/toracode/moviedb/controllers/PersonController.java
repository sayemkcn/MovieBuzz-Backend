package net.toracode.moviedb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import net.toracode.moviedb.entities.Person;
import net.toracode.moviedb.services.PersonService;

import java.util.List;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String personListPaginated(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        List<Person> personList;
        // if params aren't present return all items
        if (page == null || size == null)
            personList = this.personService.getAllPersons();
        else  // else return paginated items
            personList = this.personService.getAllPersonsPaginated(page, size);
        model.addAttribute("personList", personList);
        model.addAttribute("page", page);
        return "person/all";
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String personDetails(@PathVariable("id") Long id,Model model){
        Person person = this.personService.getPersonById(id);
        model.addAttribute("person",person);
        return "person/view";
    }

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

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updatePersonPage(@PathVariable("id") Long id, Model model) {
        Person person = this.personService.getPersonById(id);
        model.addAttribute("person", person);
        return "person/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updatePerson(@ModelAttribute Person person, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "redirect:/person/update/" + id + "?message=There's something wrong with your submission.";
        person.setUniqueId(id);
        person = this.personService.save(person);
        return "redirect:/person?message=" + person.getName() + " updated!";
    }

}
