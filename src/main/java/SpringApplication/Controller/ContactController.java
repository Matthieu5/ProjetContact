package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.data.MailRepository;
import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
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
    @Autowired
    private MailRepository mailRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/contact").setViewName("contact");
    }

    @GetMapping("/contact")
    public String listContact(Model model) {

        model.addAttribute("contactList", contactRepository.findAll());
        model.addAttribute("newContact", new Contact());

        return "ContactPage";
    }

    @GetMapping("/contact/modif/{id}")
    public String modifContact(Model model, @PathVariable long id) {

        model.addAttribute("contactList", contactRepository.findAll());
        model.addAttribute("newContact", contactRepository.findById(id).orElse(new Contact()));

        return "ContactModif";
    }

    @PostMapping("/contact/alter")
    public String postMessage(@Valid @ModelAttribute Contact newContact, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("contactList", contactRepository.findAll());
            model.addAttribute("newContact", newContact);
            return "ContactPage";
        }

        contactRepository.save(newContact);
        return "redirect:/contact";
    }



    @GetMapping("/contact/delete/{id}")
    public String deleteContact(@PathVariable long id) {
        Optional<Contact> c = contactRepository.findById(id);
        if(c.get().getMails().size() == 0) {
            contactRepository.deleteById(id);
        } else {
            for (Mail m : c.get().getMails()) {
                mailRepository.delete(m);
            }
            c.get().getMails().clear();
            contactRepository.deleteById(id);
        }

        return "redirect:contact";
    }
}
