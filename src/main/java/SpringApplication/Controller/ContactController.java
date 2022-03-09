package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ContactController implements WebMvcConfigurer {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/contact").setViewName("contact");
    }

    @GetMapping("/contact")
    public String showMessage(Model model) {

        model.addAttribute("contactList", contactRepository.findAll());
        model.addAttribute("newContact", new Contact());

        return "ContactPage";
    }

    @PostMapping("/contact")
    public String postMessage(@Valid @ModelAttribute Contact newContact, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("contactList", contactRepository.findAll());
            model.addAttribute("newContact", newContact);
            return "ContactPage";
        }

        contactRepository.save(newContact);
        return "redirect:contact";
    }

    @GetMapping("/contact/modif/{id}")
    public String showMessage(@PathVariable long id) {
        contactRepository.deleteById(id);
        return "redirect:/contact";
    }

    @DeleteMapping("/contact")
    public String deleteMessage(@ModelAttribute Contact monContact) {
        //Contact c1 = contactRepository.deleteById(monContact.getIdC());

        return "redirect:contact";
    }
}
