package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.data.MailRepository;
import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;

@Controller
public class MailController {

    @Autowired
    private MailRepository mailRepository;

    @GetMapping("/mail")
    public String showMessage(Model model) {

        model.addAttribute("mailList", mailRepository.findAll());
        model.addAttribute("newMail", new Mail());

        return "mailPage";
    }

    @PostMapping("/mail")
    public String postMessage(@Valid Mail mail, BindingResult bindingResult, @ModelAttribute Mail newMail) {

        if (bindingResult.hasErrors()) {
            return "mail";
        }

        Mail m1 = new Mail(newMail.getLibelleM());
        mailRepository.save(m1);
        return "redirect:mail";
    }

    @DeleteMapping("/mail")
    public String deleteMessage(@ModelAttribute Mail monMail) {

        Mail m1 = (Mail) mailRepository.findAll((Pageable) monMail);
        mailRepository.delete(m1);
        return "redirect:mail";
    }
}