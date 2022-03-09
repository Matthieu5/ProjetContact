package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.data.MailRepository;
import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
import SpringApplication.model.MailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MailController {

    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/mail")
    public String showMessage(Model model) {

        model.addAttribute("mailList", mailRepository.findAll());
        model.addAttribute("newMail", new MailForm());

        return "mailPage";
    }

    @PostMapping("/mail")
    public String postMessage(@Valid @ModelAttribute MailForm mailForm, BindingResult bindingResult, Model model) {
        Optional<Contact> c = contactRepository.findById(mailForm.getIdContact());

        if (bindingResult.hasErrors() || c.isEmpty()) {
            model.addAttribute("mailList", mailRepository.findAll());
            model.addAttribute("newMail", mailForm);
            return "mailPage";
        }


        Mail m1 = new Mail(mailForm.getLibelleM(), c.get());
        c.get().getMails().add(m1);
        mailRepository.save(m1);
        return "redirect:/contact";
    }

    @DeleteMapping("/mail")
    public String deleteMessage(@ModelAttribute Mail monMail) {

        Mail m1 = (Mail) mailRepository.findAll((Pageable) monMail);
        mailRepository.delete(m1);
        return "redirect:mail";
    }

    @PostMapping("/mail/alter")
    public String postMessage(@Valid @ModelAttribute Mail newMail, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("mailList", mailRepository.findAll());
            model.addAttribute("newMail", newMail);
            return "MailPage";
        }

        mailRepository.save(newMail);
        return "redirect:/mail";
    }

    @GetMapping("/mail/delete/{id}")
    public String deleteContact(@PathVariable long id) {
        mailRepository.deleteById(id);

        return "redirect:/mail";
    }
}